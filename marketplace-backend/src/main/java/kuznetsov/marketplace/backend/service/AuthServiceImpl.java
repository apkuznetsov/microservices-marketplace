package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.dto.AuthRequest;
import kuznetsov.marketplace.backend.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(), authRequest.getPassword())
        );

        String userEmail = authRequest.getEmail();
        String userRole = Optional.of(
                        authentication
                                .getAuthorities()
                                .stream()
                                .findFirst()
                ).get()
                .orElseThrow(() -> new ServiceException(AuthErrorCode.AUTH_ERROR))
                .toString();

        String accessToken = jwtService.generateAccessToken(
                userEmail, userRole);

        return AuthResponse.builder()
                .type("Bearer")
                .accessToken(accessToken)
                .role(userRole)
                .build();
    }

}
