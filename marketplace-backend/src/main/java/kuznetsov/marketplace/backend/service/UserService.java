package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.dto.UserDto;

public interface UserService {

    UserDto getUserByEmail(String userEmail);

}
