package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.domain.user.User;
import kuznetsov.marketplace.services.user.dto.UserInfoDto;

public interface UserInfoMapper {

  default UserInfoDto toUserInfo(User user) {
    return UserInfoDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .role(user.getRole())
        .build();
  }

}
