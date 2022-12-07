package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.models.user.User;
import kuznetsov.marketplace.services.user.dto.UserAuthDto;

public interface UserAuthMapper {

  default UserAuthDto toUserDto(User user) {
    return UserAuthDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .isEmailConfirmed(user.isEmailConfirmed())
        .isBanned(user.isBanned())
        .build();
  }

}
