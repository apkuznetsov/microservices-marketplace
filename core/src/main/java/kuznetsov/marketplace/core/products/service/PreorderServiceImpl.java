package kuznetsov.marketplace.core.products.service;

import jakarta.transaction.Transactional;
import kuznetsov.marketplace.core.exception.ServiceException;
import kuznetsov.marketplace.core.pagination.PageErrorCode;
import kuznetsov.marketplace.core.products.domain.PreorderInfo;
import kuznetsov.marketplace.core.products.domain.PreorderParticipation;
import kuznetsov.marketplace.core.products.domain.Product;
import kuznetsov.marketplace.core.products.domain.ProductCategory;
import kuznetsov.marketplace.core.products.domain.ProductImageUrl;
import kuznetsov.marketplace.core.products.dto.PreorderDto;
import kuznetsov.marketplace.core.products.dto.PreorderDtoPage;
import kuznetsov.marketplace.core.products.dto.PreorderParticipantsDtoPage;
import kuznetsov.marketplace.core.products.repos.PreorderParticipationRepo;
import kuznetsov.marketplace.core.products.repos.ProductCategoryRepo;
import kuznetsov.marketplace.core.products.repos.ProductImageUrlRepo;
import kuznetsov.marketplace.core.products.repos.ProductRepo;
import kuznetsov.marketplace.core.roles.customers.domain.Customer;
import kuznetsov.marketplace.core.roles.customers.repos.CustomerRepo;
import kuznetsov.marketplace.core.roles.sellers.domain.Seller;
import kuznetsov.marketplace.core.roles.sellers.repos.SellerRepo;
import kuznetsov.marketplace.core.roles.sellers.service.SellerErrorCode;
import kuznetsov.marketplace.core.users.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static kuznetsov.marketplace.core.products.service.PreorderErrorCode.PRODUCT_NOT_PREORDERABLE;
import static kuznetsov.marketplace.core.products.service.ProductCategoryErrorCode.PRODUCT_CATEGORY_NOT_FOUND;
import static kuznetsov.marketplace.core.roles.customers.service.CustomerErrorCode.CUSTOMER_NOT_FOUND;
import static kuznetsov.marketplace.core.users.service.UserErrorCode.USER_HAS_NO_PERMISSION;

@Service
@RequiredArgsConstructor
public class PreorderServiceImpl implements PreorderService {

    private final PreorderProperties preorderProps;
    private final PreorderMapper preorderMapper;
    private final PreorderValidator preorderValidator;

    private final PreorderParticipationMapper preorderParticipationMapper;
    private final PreorderParticipationPublisher preorderParticipationPublisher;

    private final ProductRepo productRepo;
    private final ProductCategoryRepo categoryRepo;
    private final ProductImageUrlRepo imageUrlRepo;
    private final SellerRepo sellerRepo;

    private final PreorderParticipationRepo preorderParticipationRepo;
    private final CustomerRepo customerRepo;

    @Override
    @Transactional
    public PreorderDto addSellerPreorder(String sellerEmail, PreorderDto preorderDto) {
        preorderValidator.validateOrThrow(preorderProps, preorderDto);

        Product savedProduct = saveAndFlushProductWithPreorder(sellerEmail, preorderDto);
        saveAllProductImageUrls(savedProduct, preorderDto.getImageUrls());

        return preorderMapper.toPreorderDto(savedProduct);
    }

    @Override
    public PreorderDto getPreorderByIdWithCurrentUserParticipationStatus(
            String userEmail, long preorderId) {

        Product product = productRepo.findById(preorderId)
                .orElseThrow(() -> new ServiceException(ProductErrorCode.PRODUCT_NOT_FOUND));

        return preorderMapper.toPreorderDto(product, userEmail);
    }

    @Override
    public PreorderDtoPage getPagedSellerPreorders(String sellerEmail, int pageNum) {
        --pageNum;
        if (pageNum < 0) {
            throw new ServiceException(PageErrorCode.NOT_POSITIVE_PAGE_NUMBER);
        }

        Seller seller = sellerRepo.findByEmail(sellerEmail)
                .orElseThrow(() -> new ServiceException(SellerErrorCode.SELLER_NOT_FOUND));
        Pageable page = PageRequest.of(pageNum, preorderProps.getPageSize());
        Page<Product> pagedProducts = productRepo
                .findAllBySellerAndPreorderInfo_IdNotNull(seller, page);

        return preorderMapper.toPreorderDtoPage(pagedProducts, sellerEmail);
    }

    @Override
    public PreorderDtoPage getPagedCustomerPreorders(String customerEmail, int pageNum) {
        --pageNum;
        if (pageNum < 0) {
            throw new ServiceException(PageErrorCode.NOT_POSITIVE_PAGE_NUMBER);
        }

        Customer customer = customerRepo.findByUserEmail(customerEmail)
                .orElseThrow(() -> new ServiceException(CUSTOMER_NOT_FOUND));
        Pageable page = PageRequest.of(pageNum, preorderProps.getPageSize());
        Page<Product> pagedProducts = productRepo
                .findAllByCustomer(customer, page);

        return preorderMapper.toPreorderDtoPage(pagedProducts, customerEmail);
    }

    @Override
    @Transactional
    public void participateCustomerInPreorderByPreorderId(String customerEmail, long preorderId) {
        Customer customer = customerRepo.findByUserEmail(customerEmail)
                .orElseThrow(() -> new ServiceException(CUSTOMER_NOT_FOUND));
        Product product = productRepo.findById(preorderId)
                .orElseThrow(() -> new ServiceException(ProductErrorCode.PRODUCT_NOT_FOUND));
        PreorderInfo preorderInfo = product.getPreorderInfo();
        if (preorderInfo == null) {
            throw new ServiceException(PRODUCT_NOT_PREORDERABLE);
        }

        PreorderParticipation participation = preorderParticipationMapper
                .toPreorderParticipation(preorderInfo, customer);
        preorderParticipationRepo.save(participation);

        preorderParticipationPublisher.publishPreorderParticipationEvent(
                customer.getPublicEmail(), Role.CUSTOMER.name());
    }

    @Override
    public PreorderParticipantsDtoPage getSellerPreorderParticipationsPageByPreorderId(
            String currentSellerEmail, long preorderId, int pageNum) {

        --pageNum;
        if (pageNum < 0) {
            throw new ServiceException(PageErrorCode.NOT_POSITIVE_PAGE_NUMBER);
        }

        Seller currentSeller = sellerRepo.findByEmail(currentSellerEmail)
                .orElseThrow(() -> new ServiceException(SellerErrorCode.SELLER_NOT_FOUND));
        Pageable page = PageRequest.of(pageNum, preorderProps.getPageSize());
        Page<PreorderParticipation> pagedCustomersParticipations = preorderParticipationRepo
                .findAllByProductId(preorderId, page);

        PreorderParticipation firstCustomerParticipation = pagedCustomersParticipations
                .getContent().get(0);
        Seller expectedSeller = firstCustomerParticipation != null
                ? firstCustomerParticipation.getPreorderInfo().getProduct().getSeller()
                : null;
        if (currentSeller != expectedSeller) {
            throw new ServiceException(USER_HAS_NO_PERMISSION);
        }

        return preorderParticipationMapper.toPreorderParticipationsDtoPage(
                preorderId, pagedCustomersParticipations);
    }

    private Product saveAndFlushProductWithPreorder(String sellerEmail, PreorderDto preorderDto) {
        Seller seller = sellerRepo.findByEmail(sellerEmail)
                .orElseThrow(() -> new ServiceException(SellerErrorCode.SELLER_NOT_FOUND));
        long categoryId = preorderDto.getCategoryId();
        ProductCategory category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ServiceException(PRODUCT_CATEGORY_NOT_FOUND));
        Product product = preorderMapper.toProduct(preorderDto, seller, category);

        return productRepo.saveAndFlush(product);
    }

    private void saveAllProductImageUrls(Product savedProduct, List<String> imageUrls) {
        if (savedProduct == null
                || imageUrls == null || imageUrls.isEmpty()) {
            return;
        }

        List<ProductImageUrl> productImageUrls = imageUrls.stream()
                .map(currUrl -> ProductImageUrl.builder()
                        .product(savedProduct)
                        .url(currUrl)
                        .build())
                .collect(Collectors.toList());

        imageUrlRepo.saveAll(productImageUrls);
    }

}
