package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.dto.PreorderDto;

public interface PreorderValidator {

    void validateOrThrow(PreorderProperties preorderProps, PreorderDto preorderDto);

}
