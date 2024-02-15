package kuznetsov.marketplace.core.products.service;

import kuznetsov.marketplace.core.products.dto.ProductDto;

public interface ProductValidator {

    void validateOrThrow(ProductProperties productProps, ProductDto productDto);

}
