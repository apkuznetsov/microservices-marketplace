package kuznetsov.marketplace.core.auth.service;

import kuznetsov.marketplace.core.auth.domain.Otp;
import kuznetsov.marketplace.core.auth.dto.OtpRequest;
import kuznetsov.marketplace.core.auth.repos.OtpRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpRepo repo;

    @Override
    public @NotNull String regenerateOtp(@NotNull String email) {
        String code = randomCode();

        Otp foundOtp = repo
                .findByEmail(email)
                .orElse(null);

        if (foundOtp != null) {
            foundOtp.setCode(code);
            return foundOtp.getEmail();
        }

        Otp newOtp = Otp.builder()
                .email(email)
                .code(code)
                .build();
        repo.save(newOtp);

        return newOtp.getCode();
    }

    @Override
    public boolean isValidOtp(@NotNull OtpRequest otp) {
        Otp foundOtp = repo
                .findByEmail(otp.getEmail())
                .orElse(null);
        if (foundOtp == null) {
            return false;
        }

        return foundOtp.getCode()
                .equals(otp.getCode());
    }

    @SneakyThrows
    private @NotNull String randomCode() {
        SecureRandom random = SecureRandom.getInstanceStrong();
        return String.valueOf(
                random.nextInt(9000) + 1000
        );
    }

}
