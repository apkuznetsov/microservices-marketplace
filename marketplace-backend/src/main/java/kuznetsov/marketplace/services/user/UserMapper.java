package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.models.user.User;
import kuznetsov.marketplace.services.user.dto.UserDto;

public interface UserMapper {

  default UserDto toUserDto(User user) {
    return UserDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .role(user.getRole())
        .build();
  }

}
