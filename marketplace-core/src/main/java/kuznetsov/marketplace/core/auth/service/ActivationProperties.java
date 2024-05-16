package kuznetsov.marketplace.core.auth.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "activation")
@Data
public class ActivationProperties {

    private String url;

}
