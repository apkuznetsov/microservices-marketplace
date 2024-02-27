package kuznetsov.marketplace.core.roles.customers.service;

import kuznetsov.marketplace.core.roles.customers.dto.CustomerDto;
import kuznetsov.marketplace.core.users.dto.UserDto;

public interface CustomerService {

    UserDto registerCustomer(String email, String password);

    CustomerDto getCustomerById(long customerId);

    CustomerDto updateCustomerById(long customerId, CustomerDto customerDto);

}
