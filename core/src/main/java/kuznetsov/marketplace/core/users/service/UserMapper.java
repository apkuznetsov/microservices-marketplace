package kuznetsov.marketplace.core.users.service;

import kuznetsov.marketplace.core.users.domain.Role;
import kuznetsov.marketplace.core.users.domain.User;
import kuznetsov.marketplace.core.users.dto.UserDto;

public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser(String email, Role role);

    User toUser(UserDto userDto);

}
