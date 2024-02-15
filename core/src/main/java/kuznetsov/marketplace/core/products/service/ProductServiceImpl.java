package kuznetsov.marketplace.core.products.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import kuznetsov.marketplace.core.exception.ServiceException;
import kuznetsov.marketplace.core.pagination.PageErrorCode;
import kuznetsov.marketplace.core.products.domain.Product;
import kuznetsov.marketplace.core.products.domain.ProductCategory;
import kuznetsov.marketplace.core.products.domain.ProductImageUrl;
import kuznetsov.marketplace.core.products.dto.ProductDto;
import kuznetsov.marketplace.core.products.dto.ProductDtoPage;
import kuznetsov.marketplace.core.products.repos.ProductCategoryRepo;
import kuznetsov.marketplace.core.products.repos.ProductImageUrlRepo;
import kuznetsov.marketplace.core.products.repos.ProductRepo;
import kuznetsov.marketplace.core.roles.sellers.domain.Seller;
import kuznetsov.marketplace.core.roles.sellers.repos.SellerRepo;
import kuznetsov.marketplace.core.roles.sellers.service.SellerErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static kuznetsov.marketplace.core.products.service.ProductCategoryErrorCode.PRODUCT_CATEGORY_NOT_FOUND;
import static kuznetsov.marketplace.core.users.service.UserErrorCode.USER_HAS_NO_PERMISSION;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductProperties productProps;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;

    private final ProductRepo productRepo;
    private final ProductCategoryRepo categoryRepo;
    private final ProductImageUrlRepo imageUrlRepo;
    private final SellerRepo sellerRepo;


    @Override
    @Transactional
    public ProductDto addSellerProduct(String sellerEmail, ProductDto productDto) {
        productValidator.validateOrThrow(productProps, productDto);

        Seller seller = sellerRepo.findByEmail(sellerEmail)
                .orElseThrow(() -> new ServiceException(SellerErrorCode.SELLER_NOT_FOUND));
        ProductCategory category = categoryRepo.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ServiceException(PRODUCT_CATEGORY_NOT_FOUND));

        Product product = productMapper.toProduct(productDto, category, seller);
        Product savedProduct = productRepo.saveAndFlush(product);

        List<ProductImageUrl> savedImageUrls = saveAllAndFlushProductImageUrls(
                savedProduct, productDto.getImageUrls());
        savedProduct.setImageUrls(savedImageUrls);

        return productMapper.toProductDto(savedProduct, category, seller);
    }

    @Override
    @Transactional
    public ProductDto updateSellerProductById(
            String sellerEmail, long productId, ProductDto productDto) {

        productValidator.validateOrThrow(productProps, productDto);

        Seller seller = sellerRepo.findByEmail(sellerEmail)
                .orElseThrow(() -> new ServiceException(SellerErrorCode.SELLER_NOT_FOUND));
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ServiceException(ProductErrorCode.PRODUCT_NOT_FOUND));
        if (seller != product.getSeller()) {
            throw new ServiceException(USER_HAS_NO_PERMISSION);
        }

        ProductCategory category = categoryRepo.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ServiceException(PRODUCT_CATEGORY_NOT_FOUND));
        imageUrlRepo.deleteAllByProduct_Id(productId);

        Product newProduct = productMapper.toProduct(productDto, category, seller);
        newProduct.setId(productId);
        Product updatedProduct = productRepo.saveAndFlush(newProduct);

        return productMapper.toProductDto(updatedProduct);
    }

    @CircuitBreaker(name = "marketplaceServer",
            fallbackMethod = "fallbackGetProductById")
    @Retry(name = "retryMarketplaceServer",
            fallbackMethod = "fallbackGetProductById")
    @Bulkhead(name = "bulkheadMarketplaceServer", type = Bulkhead.Type.THREADPOOL,
            fallbackMethod = "fallbackGetProductById")
    @Override
    public ProductDto getProductById(long productId) throws TimeoutException {
        emulateLatency();

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ServiceException(ProductErrorCode.PRODUCT_NOT_FOUND));

        return productMapper.toProductDto(product, product.getCategory(), product.getSeller());
    }

    private ProductDto fallbackGetProductById(long productId, Throwable t) {
        return ProductDto.builder()
                .description("FALLBACK PRODUCT for id = " + productId)
                .build();
    }

    @Override
    public ProductDtoPage getPagedProductsByCategoryId(long categoryId, int pageNum) {
        --pageNum;
        if (pageNum < 0) {
            throw new ServiceException(PageErrorCode.NOT_POSITIVE_PAGE_NUMBER);
        }

        Pageable page = PageRequest.of(pageNum, productProps.getPageSize());
        Page<Product> pagedProducts = productRepo.findAllByCategory_Id(categoryId, page);

        return productMapper.toProductDtoPage(pagedProducts);
    }

    @Override
    public ProductDtoPage getPagedSellerProducts(String sellerEmail, int pageNum) {
        --pageNum;
        if (pageNum < 0) {
            throw new ServiceException(PageErrorCode.NOT_POSITIVE_PAGE_NUMBER);
        }

        Seller seller = sellerRepo.findByEmail(sellerEmail)
                .orElseThrow(() -> new ServiceException(SellerErrorCode.SELLER_NOT_FOUND));
        Pageable page = PageRequest.of(pageNum, productProps.getPageSize());
        Page<Product> pagedProducts = productRepo
                .findAllBySellerAndPreorderInfo_IdIsNull(seller, page);

        return productMapper.toProductDtoPage(pagedProducts);
    }

    @Override
    public void deleteSellerProductById(String sellerEmail, long productId) {
        Seller seller = sellerRepo.findByEmail(sellerEmail)
                .orElseThrow(() -> new ServiceException(SellerErrorCode.SELLER_NOT_FOUND));
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ServiceException(ProductErrorCode.PRODUCT_NOT_FOUND));
        if (seller != product.getSeller()) {
            throw new ServiceException(USER_HAS_NO_PERMISSION);
        }
    }

    private List<ProductImageUrl> saveAllAndFlushProductImageUrls(
            Product savedProduct, List<String> imageUrls) {

        if (imageUrls == null) {
            return null;
        }

        List<ProductImageUrl> piuList = imageUrls.stream()
                .map(currUrl -> ProductImageUrl.builder()
                        .product(savedProduct)
                        .url(currUrl)
                        .build())
                .collect(Collectors.toList());

        return imageUrlRepo.saveAllAndFlush(piuList);
    }

    private void emulateLatency() throws TimeoutException {
        Random rand = new Random();
        int randomNum = rand.nextInt(3) + 1;
        if (randomNum == 3) sleep();
    }

    private void sleep() throws TimeoutException {
        try {
            Thread.sleep(5000);
            throw new TimeoutException();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

}