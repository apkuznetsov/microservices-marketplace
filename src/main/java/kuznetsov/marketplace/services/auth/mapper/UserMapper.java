package kuznetsov.marketplace.services.auth.mapper;

import kuznetsov.marketplace.domain.auth.User;
import kuznetsov.marketplace.services.auth.dto.UserDto;

public interface UserMapper {

  default UserDto toUserDto(User user) {
    return UserDto.builder()
        .email(user.getEmail())
        .isEmailConfirmed(user.isEmailConfirmed())
        .isBanned(user.isBanned())
        .build();
  }

}
