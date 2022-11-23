package kuznetsov.marketplace.services.customer.events;

import kuznetsov.marketplace.services.activation.ActivationService;
import kuznetsov.marketplace.services.activation.dto.ActivationRequest;
import kuznetsov.marketplace.services.emailing.EmailingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerRegistrationEventListener {

  private final ActivationService activationService;
  private final EmailingService emailingService;

  @EventListener
  @Async
  public void handleCustomerRegistration(CustomerRegistrationEvent event) {
    String customerEmail = event.getEmail();
    log.info("Registration event got triggered for customer with {} email.", customerEmail);
    String activationUrl = activationService.getActivationUrl();
    String activationToken = activationService.generateActivationToken(
        new ActivationRequest(
            event.getEmail(), event.getRole())
    );

    emailingService.send(customerEmail,
        "Marketplace | Activation Code",
        "You should click on the link to confirm your account.\n"
            + activationUrl
            + "/" + activationToken);
  }

}
