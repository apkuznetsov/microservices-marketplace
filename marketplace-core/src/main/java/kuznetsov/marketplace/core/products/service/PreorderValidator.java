package kuznetsov.marketplace.core.products.service;

import kuznetsov.marketplace.core.products.dto.PreorderDto;

public interface PreorderValidator {

    void validateOrThrow(PreorderProperties preorderProps, PreorderDto preorderDto);

}
