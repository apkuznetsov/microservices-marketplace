package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.models.user.User;
import kuznetsov.marketplace.models.user.UserRole;
import kuznetsov.marketplace.services.user.dto.UserAuthDto;

public interface UserAuthMapper {

  default User toCustomerUser(String email, String password) {
    return User.builder()
        .email(email)
        .password(password)
        .role(UserRole.CUSTOMER)
        .isEmailConfirmed(false)
        .isBanned(false)
        .build();
  }

  default UserAuthDto toUserDto(User user) {
    return UserAuthDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .isEmailConfirmed(user.isEmailConfirmed())
        .isBanned(user.isBanned())
        .build();
  }

}
