package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.services.user.dto.CustomerDto;

public interface CustomerService {

  CustomerDto updateCustomerById(long customerId, CustomerDto customerDto);

  CustomerDto getCustomerById(long customerId);

}
