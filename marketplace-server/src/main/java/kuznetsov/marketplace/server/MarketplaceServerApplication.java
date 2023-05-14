package kuznetsov.marketplace.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class MarketplaceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketplaceServerApplication.class, args);
    }

}
