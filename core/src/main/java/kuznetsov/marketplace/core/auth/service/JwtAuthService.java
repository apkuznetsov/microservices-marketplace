package kuznetsov.marketplace.core.auth.service;

import kuznetsov.marketplace.core.auth.dto.AuthRequest;
import kuznetsov.marketplace.core.auth.dto.JwtResponse;

public interface JwtAuthService {

    JwtResponse login(AuthRequest authRequest);

}
