package kuznetsov.marketplace.server.repository;

import kuznetsov.marketplace.server.domain.ProductImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageUrlRepository extends JpaRepository<ProductImageUrl, Long> {

    void deleteAllByProduct_Id(long productId);

}
