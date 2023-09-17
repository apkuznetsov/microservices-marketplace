package kuznetsov.marketplace.backend.roles.sellers.repos;

import kuznetsov.marketplace.backend.roles.sellers.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepo extends JpaRepository<Seller, Long> {

    @Query("SELECT seller FROM Seller seller "
            + "INNER JOIN User user ON seller.user.id = user.id "
            + "WHERE user.email =: email AND user.role = 'SELLER'")
    Optional<Seller> findByEmail(String email);

}
