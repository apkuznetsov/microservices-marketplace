package kuznetsov.marketplace.services.preorder;

import kuznetsov.marketplace.services.preorder.dto.PreorderDto;

public interface PreorderValidator {

  void validateOrThrow(PreorderProperties preorderProps, PreorderDto preorderDto);

}
