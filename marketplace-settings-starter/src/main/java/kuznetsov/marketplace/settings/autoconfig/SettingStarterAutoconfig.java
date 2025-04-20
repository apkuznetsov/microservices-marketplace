package kuznetsov.marketplace.settings.autoconfig;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@Import(SettingEntityRegistrar.class)
public class SettingStarterAutoconfig {
}
