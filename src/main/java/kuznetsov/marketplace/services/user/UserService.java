package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.services.user.dto.UserDto;

public interface UserService {

  UserDto registerCustomer(String email, String password);

  UserDto getUserByEmail(String email);

}
