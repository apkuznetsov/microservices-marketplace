package kuznetsov.marketplace.backend.auth;

public interface UserService {

    UserDto getUserByEmail(String userEmail);

}
