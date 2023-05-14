package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.dto.PreorderDto;

public interface PreorderValidator {

    void validateOrThrow(PreorderProperties preorderProps, PreorderDto preorderDto);

}
