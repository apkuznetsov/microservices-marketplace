package kuznetsov.marketplace.settings.repos;

import kuznetsov.marketplace.settings.domain.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StarterSettingRepo extends JpaRepository<ProductCategory, Long> {

    @Query("SELECT c FROM ProductCategory c ORDER BY c.id DESC")
    Page<ProductCategory> findAll(Pageable page);

}
