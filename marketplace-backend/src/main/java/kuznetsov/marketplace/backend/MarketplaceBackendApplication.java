package kuznetsov.marketplace.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class MarketplaceBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketplaceBackendApplication.class, args);
    }

}
