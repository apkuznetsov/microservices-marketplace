package kuznetsov.marketplace.backend.auth;

public interface UserAuthService {

    UserAuthDto getUserAuthByEmail(String userEmail);

    UserAuthDto confirmUserEmail(String userEmail);

    UserAuthDto registerCustomer(String email, String password);

}
