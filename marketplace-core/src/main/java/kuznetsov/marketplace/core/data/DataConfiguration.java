package kuznetsov.marketplace.core.data;

import liquibase.integration.spring.SpringLiquibase;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
@EnableConfigurationProperties(LiquibaseProperties.class)
public class DataConfiguration {

    @Bean
    @SneakyThrows
    public SpringLiquibase springLiquibase(
            @Value("${spring.liquibase.change-log}") String changeLog,
            DataSource dataSource
    ) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setLiquibaseSchema(dataSource.getConnection().getSchema());
        liquibase.setChangeLog(changeLog);

        return liquibase;
    }

    @Bean
    AuditorAware<String> auditorAware() {
        return () -> Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName);
    }

}
