package kuznetsov.marketplace.core.roles.customers.service;

import kuznetsov.marketplace.core.roles.customers.domain.Customer;
import kuznetsov.marketplace.core.roles.customers.dto.CustomerDto;
import kuznetsov.marketplace.core.users.dto.UserDto;
import kuznetsov.marketplace.core.users.service.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapperImpl implements CustomerMapper {

    private final UserMapper userMapper;

    @Override
    public Customer toCustomer(UserDto userDto) {
        return Customer.builder()
                .publicEmail(userDto.getEmail())
                .user(userMapper.toUser(userDto))
                .build();
    }

    @Override
    public Customer toCustomer(CustomerDto customerDto) {
        return Customer.builder()
                .publicEmail(customerDto.getEmail())
                .name(customerDto.getName())
                .address(customerDto.getAddress())
                .country(customerDto.getCountry())
                .build();
    }

    @Override
    public CustomerDto toCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .email(customer.getPublicEmail())
                .name(customer.getName())
                .address(customer.getAddress())
                .country(customer.getCountry())
                .build();
    }

}
