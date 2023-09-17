package kuznetsov.marketplace.backend.roles.customers.service;

import kuznetsov.marketplace.backend.roles.customers.dto.CustomerDto;
import kuznetsov.marketplace.backend.users.dto.UserDto;

public interface CustomerService {

    UserDto registerCustomer(String email, String password);

    CustomerDto getCustomerById(long customerId);

    CustomerDto updateCustomerById(long customerId, CustomerDto customerDto);

}
