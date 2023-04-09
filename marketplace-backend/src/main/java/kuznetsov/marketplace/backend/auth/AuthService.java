package kuznetsov.marketplace.backend.auth;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest);

}
