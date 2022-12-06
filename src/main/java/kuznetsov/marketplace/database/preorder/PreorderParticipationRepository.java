package kuznetsov.marketplace.database.preorder;

import kuznetsov.marketplace.domain.preorder.PreorderInfo;
import kuznetsov.marketplace.domain.preorder.PreorderParticipation;
import kuznetsov.marketplace.domain.product.Product;
import kuznetsov.marketplace.domain.user.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PreorderParticipationRepository
    extends JpaRepository<PreorderParticipation, Long> {

  @Query("SELECT participation FROM PreorderParticipation participation"
      + " INNER JOIN Product product ON participation.preorderInfo = product.preorderInfo"
      + " WHERE product = :product AND participation.customer = :customer")
  PreorderParticipation findByProductAndClient(Product product, Customer customer);

  @Query("SELECT participation FROM PreorderParticipation participation"
      + " INNER JOIN Product product ON participation.preorderInfo = product.preorderInfo"
      + " WHERE product.id = :productId")
  Page<PreorderParticipation> findAllByProductId(long productId, Pageable page);

  int countByPreorderInfo(PreorderInfo preorderInfo);

}
