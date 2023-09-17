package kuznetsov.marketplace.backend.products.service;

import kuznetsov.marketplace.backend.products.dto.PreorderParticipationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreorderParticipationPublisherImpl implements PreorderParticipationPublisher {

    private final ApplicationEventPublisher publisher;

    public void publishPreorderParticipationEvent(String customerEmail, String customerRole) {
        publisher.publishEvent(
                new PreorderParticipationEvent(customerEmail, customerRole)
        );
    }

}
