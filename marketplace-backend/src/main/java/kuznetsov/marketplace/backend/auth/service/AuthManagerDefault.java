package kuznetsov.marketplace.backend.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Profile("!keycloak")
@Component
@RequiredArgsConstructor
public class AuthManagerDefault implements AuthenticationManager {

    private final AuthenticationProvider authProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authProvider.authenticate(authentication);
    }

}
