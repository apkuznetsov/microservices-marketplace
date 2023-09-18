package kuznetsov.marketplace.backend.products.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@Configuration
public class WebCorsConfiguration implements WebMvcConfigurer {

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOriginPatterns(Collections.singletonList("*"));
        corsConfig.setAllowedMethods(Collections.singletonList(("*")));
        corsConfig.setAllowedHeaders(Collections.singletonList(("*")));
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlCorsConfig = new UrlBasedCorsConfigurationSource();
        urlCorsConfig.registerCorsConfiguration("/**", corsConfig);

        return urlCorsConfig;
    }

    @Override
    public void addCorsMappings(CorsRegistry config) {
        config.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

}
