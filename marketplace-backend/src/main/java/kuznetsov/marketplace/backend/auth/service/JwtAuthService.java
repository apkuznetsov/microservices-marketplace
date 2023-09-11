package kuznetsov.marketplace.backend.auth.service;

import kuznetsov.marketplace.backend.auth.dto.AuthRequest;
import kuznetsov.marketplace.backend.auth.dto.JwtResponse;

public interface JwtAuthService {

    JwtResponse login(AuthRequest authRequest);

}
