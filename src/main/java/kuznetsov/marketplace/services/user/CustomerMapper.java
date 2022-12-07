package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.models.user.Customer;
import kuznetsov.marketplace.models.user.User;
import kuznetsov.marketplace.services.user.dto.CustomerDto;

public interface CustomerMapper {

  default Customer toCustomer(User user) {
    return Customer.builder()
        .publicEmail(user.getEmail())
        .user(user)
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
