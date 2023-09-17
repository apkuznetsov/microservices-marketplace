package kuznetsov.marketplace.backend.roles.sellers.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "seller")
@Data
public class SellerProperties {

}
