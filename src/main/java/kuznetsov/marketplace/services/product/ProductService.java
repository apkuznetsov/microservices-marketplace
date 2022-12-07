package kuznetsov.marketplace.services.product;

import kuznetsov.marketplace.services.product.dto.ProductDto;
import kuznetsov.marketplace.services.product.dto.ProductDtoPage;

public interface ProductService {

  ProductDto addSellerProduct(String sellerEmail, ProductDto productDto);

  ProductDto updateSellerProductById(String sellerEmail, long productId, ProductDto productDto);

  ProductDto getProductById(long productId);

  ProductDtoPage getPagedProductsByCategoryId(long categoryId, int pageNum);

  ProductDtoPage getPagedSellerProducts(String sellerEmail, int pageNum);

  void deleteSellerProductById(String sellerEmail, long productId);

}
