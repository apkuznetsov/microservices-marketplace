package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.services.user.dto.CustomerDto;

public interface CustomerValidator {

  void validateOrThrow(CustomerProperties customerProps, CustomerDto customerDto);

}
