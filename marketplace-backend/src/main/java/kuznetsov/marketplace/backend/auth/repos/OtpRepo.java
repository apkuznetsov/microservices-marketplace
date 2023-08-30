package kuznetsov.marketplace.backend.auth.repos;

import kuznetsov.marketplace.backend.auth.domain.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepo extends JpaRepository<Otp, Long> {

    Optional<Otp> findByEmail(String email);

}
