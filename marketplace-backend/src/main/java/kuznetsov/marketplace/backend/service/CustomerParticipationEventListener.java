package kuznetsov.marketplace.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerParticipationEventListener {

    private final EmailingService emailingService;

    @EventListener
    @Async
    public void handleCustomerParticipation(CustomerParticipationEvent event) {
        String customerEmail = event.getEmail();
        emailingService.send(customerEmail,
                "Marketplace | Notification | Preorder",
                "You have signed up for the preorder.");
        log.info("The notification has been sent to the customer {}", customerEmail);
    }

}
