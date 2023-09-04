package kuznetsov.marketplace.backend.users.service;

import kuznetsov.marketplace.backend.users.domain.Role;
import kuznetsov.marketplace.backend.users.domain.User;
import kuznetsov.marketplace.backend.users.dto.UserDto;

public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser(String email, Role role);

    User toUser(UserDto userDto);

}
