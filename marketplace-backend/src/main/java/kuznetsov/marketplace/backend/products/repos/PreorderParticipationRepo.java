package kuznetsov.marketplace.backend.products.repos;

import kuznetsov.marketplace.backend.products.domain.PreorderInfo;
import kuznetsov.marketplace.backend.products.domain.PreorderParticipation;
import kuznetsov.marketplace.backend.products.domain.Product;
import kuznetsov.marketplace.backend.roles.customers.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PreorderParticipationRepo extends JpaRepository<PreorderParticipation, Long> {

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
