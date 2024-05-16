package kuznetsov.marketplace.core.auth.repos;

import kuznetsov.marketplace.core.auth.domain.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepo extends JpaRepository<Otp, Long> {

    Optional<Otp> findByEmail(String email);

}
