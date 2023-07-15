package kuznetsov.marketplace.backend.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "product")
@Data
public class ProductProperties {

    private Integer pageSize;

    private Integer minPrice;
    private Integer maxPrice;

}
