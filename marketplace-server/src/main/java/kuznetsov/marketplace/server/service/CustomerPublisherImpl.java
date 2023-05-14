package kuznetsov.marketplace.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerPublisherImpl implements CustomerPublisher {

    private final ApplicationEventPublisher publisher;

    public void publishCustomerRegistrationEvent(String customerEmail, String customerRole) {
        publisher.publishEvent(new CustomerRegistrationEvent(customerEmail, customerRole));
    }

}
