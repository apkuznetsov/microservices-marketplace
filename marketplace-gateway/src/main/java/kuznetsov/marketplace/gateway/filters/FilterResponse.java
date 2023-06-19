package kuznetsov.marketplace.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class FilterResponse {

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
            String correlationId = FilterUtils.getCorrelationId(requestHeaders);
            exchange.getResponse().getHeaders().add(FilterUtils.KEY_CORRELATION_ID, correlationId);
            log.debug("Completing outgoing request for {}", exchange.getRequest().getURI());
        }));
    }

}
