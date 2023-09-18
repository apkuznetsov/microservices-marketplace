package kuznetsov.marketplace.backend.auth.service;

import kuznetsov.marketplace.backend.auth.dto.AuthRequest;
import kuznetsov.marketplace.backend.auth.dto.OtpResponse;
import kuznetsov.marketplace.backend.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpAuthServiceImpl implements OtpAuthService {

    private final OtpService otpService;
    private final UserService userService;

    @Override
    public OtpResponse login(AuthRequest authRequest) {
        String email = authRequest.getEmail();
        boolean doesMatch = userService
                .doesUserPasswordMatch(email, authRequest.getPassword());
        if (!doesMatch) {
            throw new BadCredentialsException("bad credentials exception");
        }

        String code = otpService.regenerateOtp(email);
        return OtpResponse.builder()
                .email(email)
                .code(code)
                .build();
    }

}
