package kuznetsov.marketplace.core.products.repos;

import kuznetsov.marketplace.core.products.domain.ProductCategory;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Long> {

    @Query("select c from ProductCategory c order by c.id")
    @NotNull
    Page<ProductCategory> findAll(@NotNull Pageable page);

}
