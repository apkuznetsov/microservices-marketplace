package kuznetsov.marketplace.backend.auth.web;

import kuznetsov.marketplace.backend.auth.dto.AuthRequest;
import kuznetsov.marketplace.backend.auth.dto.JwtResponse;
import kuznetsov.marketplace.backend.auth.service.JwtAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JwtController {

    public static final String JWT_LOGIN_URL = "/api/v1/auth/jwt";

    private final JwtAuthService jwtAuth;

    @PostMapping(JWT_LOGIN_URL)
    public ResponseEntity<JwtResponse> loginJwt(@RequestBody AuthRequest authRequest) {
        log.debug("The user with {} email is trying to log in.", authRequest.getEmail());
        JwtResponse response = jwtAuth.login(authRequest);

        return ResponseEntity.ok(response);
    }

}
