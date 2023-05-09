package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.dto.UserAuthDto;

public interface UserAuthService {

    UserAuthDto getUserAuthByEmail(String userEmail);

    UserAuthDto confirmUserEmail(String userEmail);

    UserAuthDto registerCustomer(String email, String password);

}
