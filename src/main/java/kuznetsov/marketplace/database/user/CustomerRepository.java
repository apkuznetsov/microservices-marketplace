package kuznetsov.marketplace.database.user;

import java.util.Optional;
import kuznetsov.marketplace.domain.user.Customer;
import kuznetsov.marketplace.domain.user.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  @Query("SELECT customer FROM Customer customer "
      + "INNER JOIN User user ON customer.user.id = user.id "
      + "WHERE user.email =: email AND user.role = 'CUSTOMER'")
  Optional<Seller> findByUserEmail(String email);

}
