package kuznetsov.marketplace.models.preorder;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import kuznetsov.marketplace.models.user.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "preorder_participation",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"pp_preorder_id", "pp_client_id"})
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
