package kuznetsov.marketplace.backend.roles.customers.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "customer")
@Data
public class CustomerProperties {

    // TODO

}
