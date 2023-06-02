package kuznetsov.marketplace.server.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import kuznetsov.marketplace.server.domain.Product;
import kuznetsov.marketplace.server.domain.ProductCategory;
import kuznetsov.marketplace.server.domain.ProductImageUrl;
import kuznetsov.marketplace.server.domain.Seller;
import kuznetsov.marketplace.server.dto.ProductDto;
import kuznetsov.marketplace.server.dto.ProductDtoPage;
import kuznetsov.marketplace.server.repository.ProductCategoryRepository;
import kuznetsov.marketplace.server.repository.ProductImageUrlRepository;
import kuznetsov.marketplace.server.repository.ProductRepository;
import kuznetsov.marketplace.server.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static kuznetsov.marketplace.server.service.ProductCategoryErrorCode.PRODUCT_CATEGORY_NOT_FOUND;
import static kuznetsov.marketplace.server.service.SellerErrorCode.SELLER_NOT_FOUND;
import static kuznetsov.marketplace.server.service.UserErrorCode.USER_HAS_NO_PERMISSION;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductProperties productProps;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;

    private final ProductRepository productRepo;
    private final ProductCategoryRepository categoryRepo;
    private final ProductImageUrlRepository imageUrlRepo;
    private final SellerRepository sellerRepo;


    @Override
    @Transactional
    public ProductDto addSellerProduct(String sellerEmail, ProductDto productDto) {
        productValidator.validateOrThrow(productProps, productDto);

        Seller seller = sellerRepo.findByUserEmail(sellerEmail)
                .orElseThrow(() -> new ServiceException(SELLER_NOT_FOUND));
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

        Seller seller = sellerRepo.findByUserEmail(sellerEmail)
                .orElseThrow(() -> new ServiceException(SELLER_NOT_FOUND));
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
            fallbackMethod="fallbackGetProductById")
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

        Seller seller = sellerRepo.findByUserEmail(sellerEmail)
                .orElseThrow(() -> new ServiceException(SELLER_NOT_FOUND));
        Pageable page = PageRequest.of(pageNum, productProps.getPageSize());
        Page<Product> pagedProducts = productRepo
                .findAllBySellerAndPreorderInfo_IdIsNull(seller, page);

        return productMapper.toProductDtoPage(pagedProducts);
    }

    @Override
    public void deleteSellerProductById(String sellerEmail, long productId) {
        Seller seller = sellerRepo.findByUserEmail(sellerEmail)
                .orElseThrow(() -> new ServiceException(SELLER_NOT_FOUND));
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