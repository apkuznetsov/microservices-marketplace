package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.dto.AuthRequest;
import kuznetsov.marketplace.server.dto.AuthResponse;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest);

}
