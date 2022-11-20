package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.services.user.dto.UserDto;

public interface UserService {

  UserDto getUserByEmail(String email);

}
