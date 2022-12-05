package kuznetsov.marketplace.domain.user;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import kuznetsov.marketplace.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "seller")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seller {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "address", nullable = false, length = 1000)
  private String address;

  @Column(name = "country", nullable = false, length = 255)
  private String country;

  @Column(name = "public_email", nullable = false, length = 255)
  private String publicEmail;
  
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "s_user_id", nullable = false)
  private User user;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "p_seller_id")
  private List<Product> products;

}
