package kuznetsov.marketplace.web.auth;

import kuznetsov.marketplace.services.auth.AuthService;
import kuznetsov.marketplace.services.auth.dto.AuthRequest;
import kuznetsov.marketplace.services.auth.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  public static final String AUTH_LOGIN_URL = "/api/v1/auth/login";

  private final AuthService authService;

  @PostMapping(path = AUTH_LOGIN_URL)
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
    log.info("The user with {} email is trying to log in.", authRequest.getEmail());
    AuthResponse authResponse = authService.login(authRequest);

    return ResponseEntity.ok(authResponse);
  }

}
