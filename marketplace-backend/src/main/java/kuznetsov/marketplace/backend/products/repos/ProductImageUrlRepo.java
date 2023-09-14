package kuznetsov.marketplace.backend.products.repos;

import kuznetsov.marketplace.backend.products.domain.ProductImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageUrlRepo extends JpaRepository<ProductImageUrl, Long> {

    void deleteAllByProduct_Id(long productId);

}
