package kuznetsov.marketplace.settings.autoconfig;

import liquibase.integration.spring.SpringLiquibase;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@Import(SettingEntityRegistrar.class)
public class SettingStarterAutoconfig {

    private static final String CHANGES_LOG = "classpath:db.changelog/db.changelog-settings-starter.xml";

    @Bean
    @SneakyThrows
    public SpringLiquibase springLiquibaseSettingStarter(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setLiquibaseSchema(dataSource.getConnection().getSchema());
        liquibase.setChangeLog(CHANGES_LOG);

        return liquibase;
    }

}
