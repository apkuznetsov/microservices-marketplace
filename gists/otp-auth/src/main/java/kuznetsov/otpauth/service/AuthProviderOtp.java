package kuznetsov.otpauth.service;

import kuznetsov.otpauth.domain.AuthOtp;
import kuznetsov.otpauth.proxy.AuthServerProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthProviderOtp implements AuthenticationProvider {

    private final AuthServerProxy proxy;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String code = String.valueOf(authentication.getCredentials());
        boolean isAuthed = proxy.sendOtp(username, code);

        if (isAuthed) {
            return new AuthOtp(username, code);
        } else {
            throw new BadCredentialsException("bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return AuthOtp.class.isAssignableFrom(aClass);
    }

}
