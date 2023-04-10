package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.dto.ProductDto;

public interface ProductValidator {

    void validateOrThrow(ProductProperties productProps, ProductDto productDto);

}
