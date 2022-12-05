package kuznetsov.marketplace.database.product;

import kuznetsov.marketplace.domain.product.ProductImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageUrlRepository extends JpaRepository<ProductImageUrl, Long> {

}
