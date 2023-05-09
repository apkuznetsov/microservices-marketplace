package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.domain.Customer;
import kuznetsov.marketplace.server.domain.User;
import kuznetsov.marketplace.server.dto.CustomerDto;

public interface CustomerMapper {

    default Customer toCustomer(User user) {
        return Customer.builder()
                .publicEmail(user.getEmail())
                .user(user)
                .build();
    }

    default Customer toCustomer(CustomerDto customerDto) {
        return Customer.builder()
                .publicEmail(customerDto.getEmail())
                .name(customerDto.getName())
                .address(customerDto.getAddress())
                .country(customerDto.getCountry())
                .build();
    }

    default CustomerDto toCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .email(customer.getPublicEmail())
                .name(customer.getName())
                .address(customer.getAddress())
                .country(customer.getCountry())
                .build();
    }

}
