package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.services.user.dto.SellerDto;

public interface SellerValidator {

  void validateOrThrow(SellerProperties sellerProps, SellerDto sellerDto);

}
