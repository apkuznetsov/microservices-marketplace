package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.services.user.dto.UserDto;

public interface UserService {

  UserDto getUserByEmail(String userEmail);

  UserDto confirmUserEmail(String userEmail);

  UserDto registerCustomer(String email, String password);

}
