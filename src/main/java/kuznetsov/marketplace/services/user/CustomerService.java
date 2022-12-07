package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.services.user.dto.CustomerDto;

public interface CustomerService {

  CustomerDto getCustomerById(long customerId);

}
