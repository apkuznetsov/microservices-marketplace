package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.services.user.dto.SellerDto;
import org.springframework.stereotype.Component;

@Component
public class SellerValidatorImpl implements SellerValidator {

  @Override
  public void validateOrThrow(SellerProperties sellerProps, SellerDto sellerDto) {

  }

}
