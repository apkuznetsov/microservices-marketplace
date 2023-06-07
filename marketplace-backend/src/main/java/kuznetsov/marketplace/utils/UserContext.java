package kuznetsov.marketplace.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UserContext {

    public static final String KEY_CORRELATION_ID = "tmx-correlation-id";
    public static final String KEY_AUTH_TOKEN = "tmx-auth-token";
    public static final String KEY_USER_ID = "tmx-user-id";
    public static final String KEY_ORGANIZATION_ID = "tmx-organization-id";

    private String correlationId = "";
    private String authToken = "";
    private String userId = "";
    private String organizationId = "";

}
