package kuznetsov.marketplace.core.products.repos;

import kuznetsov.marketplace.core.products.domain.ProductImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageUrlRepo extends JpaRepository<ProductImageUrl, Long> {

    void deleteAllByProduct_Id(long productId);

}
