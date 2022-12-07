package kuznetsov.marketplace.models.product;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import kuznetsov.marketplace.models.preorder.PreorderInfo;
import kuznetsov.marketplace.models.user.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "title", nullable = false, length = 255)
  private String title;

  @Column(name = "description", nullable = false, length = 1000)
  private String description;

  @Column(name = "tech_description", nullable = false, length = 1000)
  private String techDescription;

  @Column(name = "price", nullable = false)
  private Long centPrice;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "p_category_id", nullable = false)
  private ProductCategory category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "p_seller_id", nullable = false)
  private Seller seller;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "p_preorder_id", referencedColumnName = "id")
  private PreorderInfo preorderInfo;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "piu_product_id")
  private List<ProductImageUrl> imageUrls;

}
