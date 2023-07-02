package kuznetsov.marketplace.backend.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Profile("keycloak")
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@SuppressWarnings({"removal"})
public class WebSecurityConfigurationKeycloak {

    private final WebSecurityLogoutHandlerKeycloak keycloakLogoutHandler;

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors()

                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()

                .authorizeHttpRequests()
                .requestMatchers("/**").permitAll()

                .and()
                .oauth2Login()
                .and()
                .logout()
                .addLogoutHandler(keycloakLogoutHandler)
                .logoutSuccessUrl("/")
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)

                .build();
    }

}
