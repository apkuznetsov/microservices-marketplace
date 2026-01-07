package kuznetsov.marketplace.cashback;

import kuznetsov.marketplace.cashback.config.Now;
import kuznetsov.marketplace.cashback.dao.CashbackDao;
import kuznetsov.marketplace.cashback.model.Card;
import kuznetsov.marketplace.cashback.model.CardStatus;
import kuznetsov.marketplace.cashback.model.CashbackPayment;
import kuznetsov.marketplace.cashback.model.PaymentStatus;
import kuznetsov.marketplace.cashback.model.Transaction;
import kuznetsov.marketplace.cashback.model.TransactionStatus;
import kuznetsov.marketplace.cashback.model.User;
import kuznetsov.marketplace.cashback.repository.CardRepository;
import kuznetsov.marketplace.cashback.repository.UserRepository;
import kuznetsov.marketplace.cashback.service.CashbackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class CashbackServiceTest {

    @Autowired
    private CardRepository cardRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CashbackDao cashbackDao;

    @Autowired
    private CashbackService cashbackService;

    @Test
    void accrue_cashback() {
        User user = User.builder()
                .firstName("Ivan")
                .lastName("Petrov")
                .email("ivan@test.com")
                .phone("+00000000000")
                .build();
        userRepo.save(user);

        Card card = Card.builder()
                .cardNumber("0000000000000000")
                .cardHolderName("IVAN PETROV")
                .user(user)
                .balance(new BigDecimal("10000"))
                .cashbackRate(new BigDecimal("0.02"))
                .status(CardStatus.ACTIVE)
                .build();
        cardRepo.save(card);

        BigDecimal purchaseAmount = new BigDecimal("1000");
        Transaction transaction = cashbackDao.accrueCashback(card.getId(), purchaseAmount);

        assertNotNull(transaction);
        assertEquals(TransactionStatus.COMPLETED, transaction.getStatus());
        assertEquals(new BigDecimal("20.00"), transaction.getCashbackAmount());

        Card updatedCard = cardRepo.findById(card.getId()).orElseThrow();
        assertEquals(new BigDecimal("20.00"), updatedCard.getCashbackBalance());
    }

    @Test
    void pay_cashback() {
        User user = User.builder()
                .firstName("Maria")
                .lastName("Ivanova")
                .email("maria@test.com")
                .phone("+00000000000")
                .build();
        userRepo.save(user);

        Card card = Card.builder()
                .cardNumber("0000000000000000")
                .cardHolderName("MARIA IVANOVA")
                .user(user)
                .balance(new BigDecimal("5000"))
                .cashbackBalance(new BigDecimal("150.50"))
                .status(CardStatus.ACTIVE)
                .build();
        cardRepo.save(card);

        YearMonth period = Now.yearMonth().minusMonths(1);
        CashbackPayment payment = cashbackDao.payCashback(period, card);

        assertNotNull(payment);
        assertEquals(PaymentStatus.COMPLETED, payment.getStatus());
        assertEquals(new BigDecimal("150.50"), payment.getAmount());

        Card updatedCard = cardRepo.findById(card.getId()).orElseThrow();
        assertEquals(new BigDecimal("5150.50"), updatedCard.getBalance());
        assertEquals(BigDecimal.ZERO, updatedCard.getCashbackBalance());
    }

}
