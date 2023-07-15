package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.dto.AuthRequest;
import kuznetsov.marketplace.backend.dto.AuthResponse;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest);

}
