package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.dto.UserAuthDto;

public interface UserAuthService {

    UserAuthDto getUserAuthByEmail(String userEmail);

    UserAuthDto confirmUserEmail(String userEmail);

    UserAuthDto registerCustomer(String email, String password);

}
