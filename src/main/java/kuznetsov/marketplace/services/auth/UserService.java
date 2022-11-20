package kuznetsov.marketplace.services.auth;

import kuznetsov.marketplace.services.auth.dto.UserDto;

public interface UserService {

  UserDto getUserByEmail(String email);

}
