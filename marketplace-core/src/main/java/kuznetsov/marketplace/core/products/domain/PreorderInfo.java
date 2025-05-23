package kuznetsov.marketplace.core.products.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "preorder")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class PreorderInfo {

    @Enumerated(EnumType.STRING)
    @Column(name = "preorder_status", nullable = false)
    @ColumnDefault("'STARTED'")
    PreorderStatus preorderStatus;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "cent_price_without_discount", nullable = false)
    private Long centPriceWithoutDiscount;

    @Column(name = "preorder_expected_quantity", nullable = false)
    private Integer preorderExpectedQuantity;

    @Column(name = "preorder_ends_at", nullable = false)
    private LocalDateTime preorderEndsAt;

    @CreatedBy
    private String createdBy;

    @OneToOne(mappedBy = "preorderInfo")
    private Product product;

}
