package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.dto.UserDto;

public interface UserService {

    UserDto getUserByEmail(String userEmail);

}
