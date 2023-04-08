package kuznetsov.marketplace.services.product;

import kuznetsov.marketplace.services.product.dto.ProductDto;

public interface ProductValidator {

  void validateOrThrow(ProductProperties productProps, ProductDto productDto);

}
