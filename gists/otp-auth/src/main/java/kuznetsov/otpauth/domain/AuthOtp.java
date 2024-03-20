package kuznetsov.otpauth.domain;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthOtp extends UsernamePasswordAuthenticationToken {

    public AuthOtp(Object principal, Object credentials) {
        super(principal, credentials);
    }

}
