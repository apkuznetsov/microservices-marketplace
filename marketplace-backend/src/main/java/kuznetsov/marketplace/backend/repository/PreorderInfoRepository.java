package kuznetsov.marketplace.backend.repository;

import kuznetsov.marketplace.backend.domain.PreorderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreorderInfoRepository extends JpaRepository<PreorderInfo, Long> {

}
