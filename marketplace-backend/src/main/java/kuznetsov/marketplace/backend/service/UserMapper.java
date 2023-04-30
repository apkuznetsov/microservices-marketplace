package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.domain.User;
import kuznetsov.marketplace.backend.dto.UserDto;

public interface UserMapper {

    default UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

}
