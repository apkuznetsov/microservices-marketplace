package kuznetsov.otpauth.proxy;

import kuznetsov.otpauth.dto.User;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AuthServerProxyImpl implements AuthServerProxy {

    private final RestTemplate rest;

    @Value("${auth-server.url-auth}")
    private String urlAuth;
    @Value("${auth-server.url-otp}")
    private String urlOtp;

    @Override
    public void sendAuth(@NotNull String username, @NotNull String password) {
        HttpEntity<User> request = new HttpEntity<>(
                User.builder()
                        .username(username)
                        .password(password)
                        .build()
        );

        rest.postForEntity(urlAuth, request, Void.class);
    }

    @Override
    public boolean sendOtp(@NotNull String username, @NotNull String code) {
        HttpEntity<User> request = new HttpEntity<>(
                User.builder()
                        .username(username)
                        .code(code)
                        .build()
        );

        ResponseEntity<Void> response = rest.postForEntity(urlOtp, request, Void.class);
        return response.getStatusCode().equals(HttpStatus.OK);
    }

}
