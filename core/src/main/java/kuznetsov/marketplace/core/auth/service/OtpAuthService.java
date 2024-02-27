package kuznetsov.marketplace.core.auth.service;

import kuznetsov.marketplace.core.auth.dto.AuthRequest;
import kuznetsov.marketplace.core.auth.dto.OtpResponse;

public interface OtpAuthService {

    OtpResponse login(AuthRequest authRequest);

}
