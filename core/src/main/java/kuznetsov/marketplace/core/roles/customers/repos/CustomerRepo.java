package kuznetsov.marketplace.core.roles.customers.repos;

import kuznetsov.marketplace.core.roles.customers.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    @Query("SELECT customer FROM Customer customer "
            + "INNER JOIN User user ON customer.user.id = user.id "
            + "WHERE user.email =: email AND user.role = 'CUSTOMER'")
    Optional<Customer> findByUserEmail(String email);

}
