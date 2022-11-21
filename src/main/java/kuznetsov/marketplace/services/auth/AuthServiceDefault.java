package kuznetsov.marketplace.services.auth;

import static kuznetsov.marketplace.services.auth.exception.AuthErrorCode.AUTH_ERROR;

import java.util.Optional;
import kuznetsov.marketplace.services.auth.dto.AuthRequest;
import kuznetsov.marketplace.services.auth.dto.AuthResponse;
import kuznetsov.marketplace.services.exception.ServiceException;
import kuznetsov.marketplace.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceDefault implements AuthService {

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
        .orElseThrow(() -> new ServiceException(AUTH_ERROR))
        .toString();

    String accessToken = jwtService.createAccessToken(
        userEmail, userRole);

    return AuthResponse.builder()
        .type("Bearer")
        .accessToken(accessToken)
        .role(userRole)
        .build();
  }

}
