package kuznetsov.marketplace.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreorderParticipationPublisherImpl implements PreorderParticipationPublisher {

    private final ApplicationEventPublisher publisher;

    public void publishCustomerParticipationEvent(String customerEmail, String customerRole) {
        publisher.publishEvent(new CustomerParticipationEvent(customerEmail, customerRole));
    }

}
