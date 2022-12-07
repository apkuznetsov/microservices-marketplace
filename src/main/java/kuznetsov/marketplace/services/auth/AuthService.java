package kuznetsov.marketplace.services.auth;

import kuznetsov.marketplace.services.auth.dto.AuthRequest;
import kuznetsov.marketplace.services.auth.dto.AuthResponse;

public interface AuthService {

  AuthResponse login(AuthRequest authRequest);

}
