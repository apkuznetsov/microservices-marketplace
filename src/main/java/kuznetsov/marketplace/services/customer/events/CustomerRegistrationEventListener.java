package kuznetsov.marketplace.services.customer.events;

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

  private final EmailingService emailingService;

  @EventListener
  @Async
  public void handleCustomerRegistration(CustomerRegistrationEvent event) {
    String customerEmail = event.getEmail();
    log.info("Registration event got triggered for customer with {} email.", customerEmail);
    emailingService.send(customerEmail,
        "Marketplace | Notification | Registration",
        "You have successfully registered.");
  }

}
