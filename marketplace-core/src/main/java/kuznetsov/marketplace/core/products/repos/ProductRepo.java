package kuznetsov.marketplace.core.products.repos;

import kuznetsov.marketplace.core.products.domain.Product;
import kuznetsov.marketplace.core.roles.customers.domain.Customer;
import kuznetsov.marketplace.core.roles.sellers.domain.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Page<Product> findAllBySellerAndPreorderInfo_IdIsNull(Seller seller, Pageable page);

    Page<Product> findAllBySellerAndPreorderInfo_IdNotNull(Seller seller, Pageable page);

    Page<Product> findAllByCategory_Id(long categoryId, Pageable page);

    @Query("SELECT product FROM Product product"
            + " INNER JOIN PreorderInfo preorderInfo"
            + " ON product.preorderInfo.id = preorderInfo.id"
            + " INNER JOIN PreorderParticipation participation"
            + " ON preorderInfo.id = participation.preorderInfo.id"
            + " WHERE participation.customer = :customer")
    Page<Product> findAllByCustomer(Customer customer, Pageable page);

}
