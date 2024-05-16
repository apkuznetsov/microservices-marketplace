package kuznetsov.marketplace.core.users.service;

import kuznetsov.marketplace.core.users.domain.Role;
import kuznetsov.marketplace.core.users.domain.User;
import kuznetsov.marketplace.core.users.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .isEmailConfirmed(user.isEmailConfirmed())
                .isBanned(user.isBanned())
                .build();
    }

    @Override
    public User toUser(String email, Role role) {
        return User.builder()
                .email(email)
                .role(role)
                .isEmailConfirmed(false)
                .isBanned(false)
                .build();
    }

    @Override
    public User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .password(null)
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .isEmailConfirmed(userDto.isEmailConfirmed())
                .isBanned(userDto.isBanned())
                .build();
    }

}
