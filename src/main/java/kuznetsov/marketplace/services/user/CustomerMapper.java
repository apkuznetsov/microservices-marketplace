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

  default CustomerDto toCustomerDto(User user) {
    return CustomerDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .isEmailConfirmed(user.isEmailConfirmed())
        .isBanned(user.isBanned())
        .build();
  }

}
