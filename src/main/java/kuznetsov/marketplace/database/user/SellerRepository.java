package kuznetsov.marketplace.database.user;

import java.util.Optional;
import kuznetsov.marketplace.domain.user.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

  @Query("SELECT seller FROM Seller seller "
      + "INNER JOIN User user ON seller.user.id = user.id "
      + "WHERE user.email =: email AND user.role = 'SELLER'")
  Optional<Seller> findByUserEmail(String email);

}
