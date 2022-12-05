package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.services.user.dto.CustomerDto;

public interface CustomerService {

  CustomerDto registerCustomer(String email, String password);

}
