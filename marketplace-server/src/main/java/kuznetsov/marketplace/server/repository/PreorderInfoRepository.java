package kuznetsov.marketplace.server.repository;

import kuznetsov.marketplace.server.domain.PreorderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreorderInfoRepository extends JpaRepository<PreorderInfo, Long> {

}
