package kuznetsov.marketplace.backend.products.service;

import kuznetsov.marketplace.backend.products.dto.ProductDto;

public interface ProductValidator {

    void validateOrThrow(ProductProperties productProps, ProductDto productDto);

}
