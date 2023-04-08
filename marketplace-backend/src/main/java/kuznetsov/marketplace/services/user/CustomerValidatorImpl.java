package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.services.user.dto.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidatorImpl implements CustomerValidator {

  @Override
  public void validateOrThrow(CustomerProperties customerProps, CustomerDto customerDto) {

  }

}
