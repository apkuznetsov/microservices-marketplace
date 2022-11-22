package kuznetsov.marketplace.services.customer.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerRegistrationEventListener {

  @EventListener
  @Async
  public void handleCustomerRegistration(CustomerRegistrationEvent event) {
    log.info("Registration event got triggered for customer with {} email.", event.getEmail());
  }

}
