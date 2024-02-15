package kuznetsov.marketplace.core.roles.customers.service;

import kuznetsov.marketplace.core.roles.customers.domain.Customer;
import kuznetsov.marketplace.core.roles.customers.dto.CustomerDto;
import kuznetsov.marketplace.core.users.dto.UserDto;

public interface CustomerMapper {

    Customer toCustomer(UserDto userDto);

    Customer toCustomer(CustomerDto customerDto);

    CustomerDto toCustomerDto(Customer customer);

}
