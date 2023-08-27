package kuznetsov.marketplace.backend.auth.service;

import kuznetsov.marketplace.backend.auth.dto.OtpRequest;
import org.jetbrains.annotations.NotNull;

public interface OtpService {

    @NotNull String regenerateOtp(@NotNull String email);

    boolean isValidOtp(@NotNull OtpRequest otp);

}
