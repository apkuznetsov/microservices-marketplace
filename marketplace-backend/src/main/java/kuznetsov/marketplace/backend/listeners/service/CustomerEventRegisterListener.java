package kuznetsov.marketplace.backend.listeners.service;

import kuznetsov.marketplace.backend.auth.dto.ActivationRequest;
import kuznetsov.marketplace.backend.auth.service.ActivationService;
import kuznetsov.marketplace.backend.emailing.proxy.EmailingProxy;
import kuznetsov.marketplace.backend.roles.customers.dto.CustomerEventRegister;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerEventRegisterListener {

    private final ActivationService activationService;
    private final EmailingProxy emailingProxy;

    @EventListener
    @Async
    public void handleCustomerEventRegister(CustomerEventRegister event) {
        String customerEmail = event.getEmail();
        log.debug("registration event got triggered for customer with {} email.", customerEmail);

        String activationUrl = activationService.getActivationUrl();
        String activationToken = activationService.generateActivationToken(
                new ActivationRequest(
                        event.getEmail(), event.getRole())
        );

        emailingProxy.send(customerEmail,
                "Marketplace | Activation Code",
                "You should click on the link to confirm your account.\n"
                        + activationUrl
                        + "/" + activationToken);
    }

}
