package kuznetsov.otpauth.domain;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthUsernamePassword extends UsernamePasswordAuthenticationToken {

    public AuthUsernamePassword(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public AuthUsernamePassword(Object principal, Object credentials) {
        super(principal, credentials);
    }

}
