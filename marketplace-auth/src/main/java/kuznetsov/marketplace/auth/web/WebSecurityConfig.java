package kuznetsov.marketplace.auth.web;

import kuznetsov.marketplace.auth.service.AuthFilterInitial;
import kuznetsov.marketplace.auth.service.AuthFilterJwt;
import kuznetsov.marketplace.auth.service.AuthProviderOtp;
import kuznetsov.marketplace.auth.service.AuthProviderUsernamePassword;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@SuppressWarnings({"removal"})
public class WebSecurityConfig {

    private final AuthFilterInitial initFilter;
    private final AuthFilterJwt jwtFilter;

    private final AuthProviderOtp otpProvider;
    private final AuthProviderUsernamePassword passProvider;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(otpProvider)
                .authenticationProvider(passProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .httpBasic().disable()

                .authorizeHttpRequests()
                .requestMatchers("/**").permitAll()
                .and()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .addFilterAt(
                        initFilter,
                        BasicAuthenticationFilter.class)
                .addFilterAfter(
                        jwtFilter,
                        BasicAuthenticationFilter.class
                )

                .build();
    }

}
