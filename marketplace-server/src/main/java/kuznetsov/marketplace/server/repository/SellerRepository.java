package kuznetsov.marketplace.server.repository;

import kuznetsov.marketplace.server.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("SELECT seller FROM Seller seller "
            + "INNER JOIN User user ON seller.user.id = user.id "
            + "WHERE user.email =: email AND user.role = 'SELLER'")
    Optional<Seller> findByUserEmail(String email);

}
