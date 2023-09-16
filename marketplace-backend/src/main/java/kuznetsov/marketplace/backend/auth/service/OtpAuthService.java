package kuznetsov.marketplace.backend.auth.service;

import kuznetsov.marketplace.backend.auth.dto.AuthRequest;
import kuznetsov.marketplace.backend.auth.dto.OtpResponse;

public interface OtpAuthService {

    OtpResponse login(AuthRequest authRequest);

}
