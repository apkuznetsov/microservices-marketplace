package kuznetsov.marketplace.core.roles.customers.service;

import kuznetsov.marketplace.core.roles.customers.dto.CustomerEventRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerPublisherImpl implements CustomerPublisher {

    private final ApplicationEventPublisher publisher;

    public void publishCustomerEventRegister(String customerEmail, String customerRole) {
        publisher.publishEvent(
                new CustomerEventRegister(customerEmail, customerRole)
        );
    }

}
