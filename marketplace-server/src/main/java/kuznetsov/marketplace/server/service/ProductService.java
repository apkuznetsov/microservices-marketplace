package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.dto.ProductDto;
import kuznetsov.marketplace.server.dto.ProductDtoPage;

public interface ProductService {

    ProductDto addSellerProduct(String sellerEmail, ProductDto productDto);

    ProductDto updateSellerProductById(String sellerEmail, long productId, ProductDto productDto);

    ProductDto getProductById(long productId);

    ProductDtoPage getPagedProductsByCategoryId(long categoryId, int pageNum);

    ProductDtoPage getPagedSellerProducts(String sellerEmail, int pageNum);

    void deleteSellerProductById(String sellerEmail, long productId);

}
