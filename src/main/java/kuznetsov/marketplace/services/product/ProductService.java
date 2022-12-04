package kuznetsov.marketplace.services.product;

import kuznetsov.marketplace.services.product.dto.ProductDto;

public interface ProductService {

  ProductDto addSellerProduct(String sellerEmail, ProductDto productDto);

}
