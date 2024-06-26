package kuznetsov.marketplace.core.products.service;

import kuznetsov.marketplace.core.products.dto.ProductDto;
import kuznetsov.marketplace.core.products.dto.ProductDtoPage;

import java.util.concurrent.TimeoutException;

public interface ProductService {

    ProductDto addSellerProduct(String sellerEmail, ProductDto productDto);

    ProductDto updateSellerProductById(String sellerEmail, long productId, ProductDto productDto);

    ProductDto getProductById(long productId) throws TimeoutException;

    ProductDtoPage getPagedProductsByCategoryId(long categoryId, int pageNum);

    ProductDtoPage getPagedSellerProducts(String sellerEmail, int pageNum);

    void deleteSellerProductById(String sellerEmail, long productId);

}
