package kuznetsov.marketplace.core.products.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "preorder")
@Data
public class PreorderProperties {

    private Integer pageSize;

    private Integer minPrice;
    private Integer maxPrice;

    private Integer minPreorderDurationDays;
    private Integer maxPreorderDurationDays;

    private Integer minPreorderExpectedQuantity;
    private Integer maxPreorderExpectedQuantity;

}
