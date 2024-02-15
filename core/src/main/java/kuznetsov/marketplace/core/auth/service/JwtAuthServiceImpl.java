package kuznetsov.marketplace.core.auth.service;

import kuznetsov.marketplace.core.auth.dto.AuthRequest;
import kuznetsov.marketplace.core.auth.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtAuthServiceImpl implements JwtAuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public JwtResponse login(AuthRequest authRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(), authRequest.getPassword())
        );

        String email = authRequest.getEmail();
        String role = Optional.of(
                        auth.getAuthorities()
                                .stream()
                                .findFirst()
                ).get()
                .orElseThrow(() -> new BadCredentialsException("bad credentials exception"))
                .toString();
        String token = jwtService.generateAccessToken(
                email, role);

        return JwtResponse.builder()
                .type("Bearer")
                .accessToken(token)
                .role(role)
                .build();
    }

}
