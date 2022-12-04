package kuznetsov.marketplace.database.product;

import kuznetsov.marketplace.domain.product.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

  @Query("select c from ProductCategory c order by c.id")
  Page<ProductCategory> findAll(Pageable page);

}
