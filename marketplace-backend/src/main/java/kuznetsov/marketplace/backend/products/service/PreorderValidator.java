package kuznetsov.marketplace.backend.products.service;

import kuznetsov.marketplace.backend.products.dto.PreorderDto;

public interface PreorderValidator {

    void validateOrThrow(PreorderProperties preorderProps, PreorderDto preorderDto);

}
