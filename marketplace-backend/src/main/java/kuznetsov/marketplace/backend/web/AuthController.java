package kuznetsov.marketplace.backend.web;

import kuznetsov.marketplace.backend.dto.AuthRequest;
import kuznetsov.marketplace.backend.dto.AuthResponse;
import kuznetsov.marketplace.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    public static final String AUTH_URL = "/api/v1/auth";

    private final AuthService authService;

    @PostMapping(path = AUTH_URL)
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        log.info("The user with {} email is trying to log in.", authRequest.getEmail());
        AuthResponse authResponse = authService.login(authRequest);

        return ResponseEntity.ok(authResponse);
    }

}
