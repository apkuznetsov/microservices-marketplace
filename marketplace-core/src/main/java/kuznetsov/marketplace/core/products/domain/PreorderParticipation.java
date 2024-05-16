package kuznetsov.marketplace.core.products.domain;

import jakarta.annotation.PostConstruct;
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
import jakarta.persistence.UniqueConstraint;
import kuznetsov.marketplace.core.roles.customers.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "preorder_participation",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"pp_preorder_id", "pp_customer_id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreorderParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "participation_status", nullable = false)
    @ColumnDefault("'CLIENT_AWAITING_PAYMENT'")
    private PreorderParticipationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pp_preorder_id", nullable = false, updatable = false)
    private PreorderInfo preorderInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pp_customer_id", nullable = false, updatable = false)
    private Customer customer;

    @PostConstruct
    private void init() {
        status = PreorderParticipationStatus.CLIENT_AWAITING_PAYMENT;
    }

}
