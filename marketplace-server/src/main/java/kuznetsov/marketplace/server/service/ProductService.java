package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.dto.ProductDto;
import kuznetsov.marketplace.server.dto.ProductDtoPage;

import java.util.concurrent.TimeoutException;

public interface ProductService {

    ProductDto addSellerProduct(String sellerEmail, ProductDto productDto);

    ProductDto updateSellerProductById(String sellerEmail, long productId, ProductDto productDto);

    ProductDto getProductById(long productId) throws TimeoutException;

    ProductDtoPage getPagedProductsByCategoryId(long categoryId, int pageNum);

    ProductDtoPage getPagedSellerProducts(String sellerEmail, int pageNum);

    void deleteSellerProductById(String sellerEmail, long productId);

}
