package kuznetsov.marketplace.backend.repository;

import kuznetsov.marketplace.backend.domain.ProductImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageUrlRepository extends JpaRepository<ProductImageUrl, Long> {

    void deleteAllByProduct_Id(long productId);

}
