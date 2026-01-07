package kuznetsov.marketplace.cashback.dev;

import jakarta.annotation.PostConstruct;
import kuznetsov.marketplace.cashback.model.Card;
import kuznetsov.marketplace.cashback.model.CardStatus;
import kuznetsov.marketplace.cashback.model.User;
import kuznetsov.marketplace.cashback.repository.CardRepository;
import kuznetsov.marketplace.cashback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class TestDataConfig {

    private final UserRepository userRepo;
    private final CardRepository cardRepo;

    //@PostConstruct
    @Transactional
    public void initTestData() {
        log.info("Loading test data...");

        User user1 = User.builder()
                .firstName("Иван")
                .lastName("Петров")
                .email("ivan.petrov@example.com")
                .phone("+79001234567")
                .build();
        Card card1 = Card.builder()
                .cardNumber("4276123456789012")
                .cardHolderName("IVAN PETROV")
                .user(user1)
                .balance(new BigDecimal("50000.00"))
                .cashbackBalance(new BigDecimal("250.50"))
                .cashbackRate(new BigDecimal("0.015"))
                .status(CardStatus.ACTIVE)
                .build();
        userRepo.save(user1);
        cardRepo.save(card1);

        User user2 = User.builder()
                .firstName("Мария")
                .lastName("Иванова")
                .email("maria.ivanova@example.com")
                .phone("+79009876543")
                .build();
        Card card2 = Card.builder()
                .cardNumber("5536987654321098")
                .cardHolderName("MARIA IVANOVA")
                .user(user2)
                .balance(new BigDecimal("75000.00"))
                .cashbackBalance(new BigDecimal("450.75"))
                .cashbackRate(new BigDecimal("0.02"))
                .status(CardStatus.ACTIVE)
                .build();
        userRepo.save(user2);
        cardRepo.save(card2);

        log.info("Test data loaded successfully");
    }

}
