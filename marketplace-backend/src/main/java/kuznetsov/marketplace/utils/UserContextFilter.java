package kuznetsov.marketplace.utils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class UserContextFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        UserContextHolder.getContext().setCorrelationId(
                httpServletRequest.getHeader(UserContext.KEY_CORRELATION_ID));
        UserContextHolder.getContext().setUserId(
                httpServletRequest.getHeader(UserContext.KEY_USER_ID));
        UserContextHolder.getContext().setAuthToken(
                httpServletRequest.getHeader(UserContext.KEY_AUTH_TOKEN));
        UserContextHolder.getContext().setOrganizationId(
                httpServletRequest.getHeader(UserContext.KEY_ORGANIZATION_ID));

        log.debug("UserContextFilter correlation id: {}",
                UserContextHolder.getContext().getCorrelationId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

}
