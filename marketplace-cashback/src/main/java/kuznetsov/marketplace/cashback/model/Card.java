package kuznetsov.marketplace.cashback.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kuznetsov.marketplace.cashback.config.Now;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cardNumber;

    @Column(nullable = false)
    private String cardHolderName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal cashbackBalance = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal cashbackRate = new BigDecimal("0.01");

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CardStatus status = CardStatus.ACTIVE;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = Now.localDateTime();

    private LocalDateTime lastCashbackPaymentDate;

}
