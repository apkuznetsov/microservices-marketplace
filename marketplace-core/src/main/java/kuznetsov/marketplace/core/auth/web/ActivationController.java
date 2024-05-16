package kuznetsov.marketplace.core.auth.web;

import kuznetsov.marketplace.core.auth.dto.ActivationResponse;
import kuznetsov.marketplace.core.auth.service.ActivationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ActivationController {

    public static final String ACTIVATION_URL = "/api/v1/activation";

    private final ActivationService activationService;

    @GetMapping(path = ACTIVATION_URL + "/{activationToken}")
    public ResponseEntity<ActivationResponse> activate(@PathVariable String activationToken) {
        log.info("The system is trying to activate token {}.", activationToken);
        ActivationResponse message = activationService.activate(activationToken);

        return ResponseEntity.ok(message);
    }

}
