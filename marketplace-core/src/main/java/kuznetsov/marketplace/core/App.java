package kuznetsov.marketplace.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication(scanBasePackages = {
        "kuznetsov.marketplace.core",
        "kuznetsov.marketplace.settings"})
@RefreshScope
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
