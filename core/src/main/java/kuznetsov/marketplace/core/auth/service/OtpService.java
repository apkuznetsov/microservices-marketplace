package kuznetsov.marketplace.core.auth.service;

import kuznetsov.marketplace.core.auth.dto.OtpRequest;
import org.jetbrains.annotations.NotNull;

public interface OtpService {

    @NotNull String regenerateOtp(@NotNull String email);

    boolean isValidOtp(@NotNull OtpRequest otp);

}
