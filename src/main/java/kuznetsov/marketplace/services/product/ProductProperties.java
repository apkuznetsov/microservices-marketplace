package kuznetsov.marketplace.services.product;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "product")
@Data
public class ProductProperties {

  private Integer pageSize;

}
