package kuznetsov.marketplace.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MarketplaceGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketplaceGatewayApplication.class, args);
    }

}
