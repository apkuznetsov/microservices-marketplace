package kuznetsov.marketplace.backend.auth.web;

import kuznetsov.marketplace.backend.auth.dto.AuthRequest;
import kuznetsov.marketplace.backend.auth.dto.OtpRequest;
import kuznetsov.marketplace.backend.auth.dto.OtpResponse;
import kuznetsov.marketplace.backend.auth.service.OtpAuthService;
import kuznetsov.marketplace.backend.auth.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OtpController {

    public static final String OTP_LOGIN_URL = "/api/v1/auth/otp";
    public static final String OTP_CHECK_URL = "/api/v1/otp/check";

    private final OtpAuthService otpAuth;
    private final OtpService otpService;

    @PostMapping(OTP_LOGIN_URL)
    public ResponseEntity<OtpResponse> loginOtp(@RequestBody AuthRequest authRequest) {
        log.debug("The user with {} email is trying to log in.", authRequest.getEmail());
        OtpResponse response = otpAuth.login(authRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping(OTP_CHECK_URL)
    public ResponseEntity<?> checkOtp(@RequestBody OtpRequest otp) {
        if (otpService.isValidOtp(otp)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).build();
        }
    }

}
