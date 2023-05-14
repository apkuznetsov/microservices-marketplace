package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.domain.User;
import kuznetsov.marketplace.server.dto.UserDto;

public interface UserMapper {

    default UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

}
