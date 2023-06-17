package kuznetsov.marketplace.gateway.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

public class FilterUtils {

    public static final String KEY_CORRELATION_ID = "tmx-correlation-id";
    public static final String KEY_AUTH_TOKEN = "tmx-auth-token";
    public static final String KEY_USER_ID = "tmx-user-id";
    public static final String KEY_ORG_ID = "tmx-org-id";
    public static final String FILTER_TYPE_PRE = "pre";
    public static final String FILTER_TYPE_POST = "post";
    public static final String FILTER_TYPE_ROUTE = "route";

    public static String getCorrelationId(HttpHeaders requestHeaders) {
        if (requestHeaders == null) {
            return null;
        }

        List<String> header = requestHeaders.get(KEY_CORRELATION_ID);
        if (header == null || header.isEmpty()) {
            return null;
        }

        return header.stream()
                .findFirst()
                .get();
    }

    public static ServerWebExchange setRequestHeader(ServerWebExchange exchange, String key, String value) {
        return exchange.mutate().request(
                exchange.getRequest().mutate()
                        .header(key, value)
                        .build()
        ).build();
    }

    public static ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return setRequestHeader(exchange, KEY_CORRELATION_ID, correlationId);
    }

}
