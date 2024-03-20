package kuznetsov.otpauth.proxy;

import org.jetbrains.annotations.NotNull;

public interface AuthServerProxy {

    void sendAuth(@NotNull String username, @NotNull String password);

    boolean sendOtp(@NotNull String username, @NotNull String code);

}
