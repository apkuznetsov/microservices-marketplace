package kuznetsov.marketplace.services.user.mapper;

import kuznetsov.marketplace.domain.user.User;
import kuznetsov.marketplace.domain.user.UserRole;
import kuznetsov.marketplace.services.user.dto.UserDto;

public interface UserMapper {

  default User toCustomerUser(String email, String password) {
    return User.builder()
        .email(email)
        .password(password)
        .role(UserRole.CUSTOMER)
        .isEmailConfirmed(false)
        .isBanned(false)
        .build();
  }

  default UserDto toUserDto(User user) {
    return UserDto.builder()
        .email(user.getEmail())
        .isEmailConfirmed(user.isEmailConfirmed())
        .isBanned(user.isBanned())
        .build();
  }

}
