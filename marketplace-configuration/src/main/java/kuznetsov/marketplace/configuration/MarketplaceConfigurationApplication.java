package kuznetsov.marketplace.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class MarketplaceConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketplaceConfigurationApplication.class, args);
    }

}
