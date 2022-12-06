package kuznetsov.marketplace.services.preorder;

import static kuznetsov.marketplace.services.pagination.PageErrorCode.NOT_POSITIVE_PAGE_NUMBER;
import static kuznetsov.marketplace.services.preorder.PreorderErrorCode.PRODUCT_NOT_PREORDERABLE;
import static kuznetsov.marketplace.services.product.ProductCategoryErrorCode.PRODUCT_CATEGORY_NOT_FOUND;
import static kuznetsov.marketplace.services.product.ProductErrorCode.PRODUCT_NOT_FOUND;
import static kuznetsov.marketplace.services.user.CustomerErrorCode.CUSTOMER_NOT_FOUND;
import static kuznetsov.marketplace.services.user.SellerErrorCode.SELLER_NOT_FOUND;
import static kuznetsov.marketplace.services.user.UserErrorCode.USER_HAS_NO_PERMISSION;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import kuznetsov.marketplace.database.preorder.PreorderParticipationRepository;
import kuznetsov.marketplace.database.product.ProductCategoryRepository;
import kuznetsov.marketplace.database.product.ProductImageUrlRepository;
import kuznetsov.marketplace.database.product.ProductRepository;
import kuznetsov.marketplace.database.user.CustomerRepository;
import kuznetsov.marketplace.database.user.SellerRepository;
import kuznetsov.marketplace.domain.preorder.PreorderInfo;
import kuznetsov.marketplace.domain.preorder.PreorderParticipation;
import kuznetsov.marketplace.domain.product.Product;
import kuznetsov.marketplace.domain.product.ProductCategory;
import kuznetsov.marketplace.domain.product.ProductImageUrl;
import kuznetsov.marketplace.domain.user.Customer;
import kuznetsov.marketplace.domain.user.Seller;
import kuznetsov.marketplace.domain.user.UserRole;
import kuznetsov.marketplace.services.exception.ServiceException;
import kuznetsov.marketplace.services.preorder.dto.PreorderDto;
import kuznetsov.marketplace.services.preorder.dto.PreorderDtoPage;
import kuznetsov.marketplace.services.preorder.dto.PreorderParticipantsDtoPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
      throw new ServiceException(NOT_POSITIVE_PAGE_NUMBER);
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
      throw new ServiceException(NOT_POSITIVE_PAGE_NUMBER);
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
      throw new ServiceException(NOT_POSITIVE_PAGE_NUMBER);
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
