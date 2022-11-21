package kuznetsov.marketplace.services.customer;

import kuznetsov.marketplace.services.customer.dto.CustomerDto;

public interface CustomerService {

  CustomerDto registerCustomer(String email, String password);

}
