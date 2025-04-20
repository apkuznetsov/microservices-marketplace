package kuznetsov.marketplace.settings.repo;

import kuznetsov.marketplace.settings.domain.SettingStarter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepoStarter extends JpaRepository<SettingStarter, Long> {

    @Query("SELECT s FROM SettingStarter s ORDER BY s.id DESC")
    Page<SettingStarter> findAll(Pageable page);

}
