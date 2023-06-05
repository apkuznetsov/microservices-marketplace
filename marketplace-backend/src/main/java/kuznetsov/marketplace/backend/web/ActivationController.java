package kuznetsov.marketplace.backend.web;

import kuznetsov.marketplace.backend.dto.ActivationMessage;
import kuznetsov.marketplace.backend.service.ActivationService;
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
    public ResponseEntity<ActivationMessage> activate(@PathVariable String activationToken) {
        log.info("The system is trying to activate token {}.", activationToken);
        ActivationMessage message = activationService.activate(activationToken);

        return ResponseEntity.ok(message);
    }

}
