package kuznetsov.marketplace.backend.listeners.service;

import kuznetsov.marketplace.backend.emailing.proxy.EmailingProxy;
import kuznetsov.marketplace.backend.products.dto.PreorderParticipationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PreorderParticipationEventListener {

    private final EmailingProxy emailingProxy;

    @EventListener
    @Async
    public void handlePreorderParticipationEvent(PreorderParticipationEvent event) {
        String customerEmail = event.getEmail();
        emailingProxy.send(customerEmail,
                "Marketplace | Notification | Preorder",
                "You have signed up for the preorder.");
        log.debug("The notification has been sent to the customer {}", customerEmail);
    }

}
