package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.domain.Customer;
import kuznetsov.marketplace.backend.domain.PreorderInfo;
import kuznetsov.marketplace.backend.domain.PreorderParticipation;
import kuznetsov.marketplace.backend.domain.Product;
import kuznetsov.marketplace.backend.domain.ProductCategory;
import kuznetsov.marketplace.backend.domain.ProductImageUrl;
import kuznetsov.marketplace.backend.domain.Seller;
import kuznetsov.marketplace.backend.domain.UserRole;
import kuznetsov.marketplace.backend.dto.PreorderDto;
import kuznetsov.marketplace.backend.dto.PreorderDtoPage;
import kuznetsov.marketplace.backend.dto.PreorderParticipantsDtoPage;
import kuznetsov.marketplace.backend.exception.ServiceException;
import kuznetsov.marketplace.backend.pagination.PageErrorCode;
import kuznetsov.marketplace.backend.repository.CustomerRepository;
import kuznetsov.marketplace.backend.repository.PreorderParticipationRepository;
import kuznetsov.marketplace.backend.repository.ProductCategoryRepository;
import kuznetsov.marketplace.backend.repository.ProductImageUrlRepository;
import kuznetsov.marketplace.backend.repository.ProductRepository;
import kuznetsov.marketplace.backend.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static kuznetsov.marketplace.backend.auth.UserErrorCode.USER_HAS_NO_PERMISSION;
import static kuznetsov.marketplace.backend.service.CustomerErrorCode.CUSTOMER_NOT_FOUND;
import static kuznetsov.marketplace.backend.service.PreorderErrorCode.PRODUCT_NOT_PREORDERABLE;
import static kuznetsov.marketplace.backend.service.ProductCategoryErrorCode.PRODUCT_CATEGORY_NOT_FOUND;
import static kuznetsov.marketplace.backend.service.ProductErrorCode.PRODUCT_NOT_FOUND;
import static kuznetsov.marketplace.backend.service.SellerErrorCode.SELLER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PreorderServiceImpl implements PreorderService {

    private final PreorderProperties preorderProps;
    private final PreorderMapper preorderMapper;
    private final PreorderValidator preorderValidator;

    private final PreorderParticipationMapper preorderParticipationMapper;
    private final PreorderParticipationPublisher preorderParticipationPublisher;

    private final ProductRepository productRepo;
    private final ProductCategoryRepository categoryRepo;
    private final ProductImageUrlRepository imageUrlRepo;
    private final SellerRepository sellerRepo;

    private final PreorderParticipationRepository preorderParticipationRepo;
    private final CustomerRepository customerRepo;

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
                .orElseThrow(() -> new ServiceException(PRODUCT_NOT_FOUND));

        return preorderMapper.toPreorderDto(product, userEmail);
    }

    @Override
    public PreorderDtoPage getPagedSellerPreorders(String sellerEmail, int pageNum) {
        --pageNum;
        if (pageNum < 0) {
            throw new ServiceException(PageErrorCode.NOT_POSITIVE_PAGE_NUMBER);
        }

        Seller seller = sellerRepo.findByUserEmail(sellerEmail)
                .orElseThrow(() -> new ServiceException(SELLER_NOT_FOUND));
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
                .orElseThrow(() -> new ServiceException(PRODUCT_NOT_FOUND));
        PreorderInfo preorderInfo = product.getPreorderInfo();
        if (preorderInfo == null) {
            throw new ServiceException(PRODUCT_NOT_PREORDERABLE);
        }

        PreorderParticipation participation = preorderParticipationMapper
                .toPreorderParticipation(preorderInfo, customer);
        preorderParticipationRepo.save(participation);

        preorderParticipationPublisher.publishCustomerParticipationEvent(
                customer.getPublicEmail(), UserRole.CUSTOMER.name());
    }

    @Override
    public PreorderParticipantsDtoPage getSellerPreorderParticipationsPageByPreorderId(
            String currentSellerEmail, long preorderId, int pageNum) {

        --pageNum;
        if (pageNum < 0) {
            throw new ServiceException(PageErrorCode.NOT_POSITIVE_PAGE_NUMBER);
        }

        Seller currentSeller = sellerRepo.findByUserEmail(currentSellerEmail)
                .orElseThrow(() -> new ServiceException(SELLER_NOT_FOUND));
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
        Seller seller = sellerRepo.findByUserEmail(sellerEmail)
                .orElseThrow(() -> new ServiceException(SELLER_NOT_FOUND));
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
