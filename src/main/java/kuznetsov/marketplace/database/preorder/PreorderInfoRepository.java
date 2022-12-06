package kuznetsov.marketplace.database.preorder;

import kuznetsov.marketplace.models.preorder.PreorderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreorderInfoRepository extends JpaRepository<PreorderInfo, Long> {

}
