package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.dto.ProductDto;

public interface ProductValidator {

    void validateOrThrow(ProductProperties productProps, ProductDto productDto);

}
