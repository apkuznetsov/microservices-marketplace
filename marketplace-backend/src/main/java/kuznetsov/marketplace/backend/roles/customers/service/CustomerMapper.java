package kuznetsov.marketplace.backend.roles.customers.service;

import kuznetsov.marketplace.backend.roles.customers.domain.Customer;
import kuznetsov.marketplace.backend.roles.customers.dto.CustomerDto;
import kuznetsov.marketplace.backend.users.dto.UserDto;

public interface CustomerMapper {

    Customer toCustomer(UserDto userDto);

    Customer toCustomer(CustomerDto customerDto);

    CustomerDto toCustomerDto(Customer customer);

}
