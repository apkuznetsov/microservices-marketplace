package kuznetsov.marketplace.services.customer.events;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CustomerRegistrationEvent {

  private final String email;

}
