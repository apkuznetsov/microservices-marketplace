package kuznetsov.marketplace.core.auth.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuznetsov.marketplace.core.users.dto.UserDto;
import kuznetsov.marketplace.core.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String bearer = request.getHeader("Authorization");
        String token = (StringUtils.hasText(bearer) && bearer.startsWith("Bearer "))
                ? bearer.substring(7)
                : null;

        if (token == null
                || token.isEmpty()
                || !jwtService.isValidAccessToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtService.getEmailFromAccessToken(token);
        UserDto user = userService.getUserByEmailOrNull(email);
        if (user.isBanned() || !user.isEmailConfirmed()) {
            filterChain.doFilter(request, response);
            return;
        }

        Set<SimpleGrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(
                jwtService.getRoleFromAccessToken(token)
        ));

        Authentication auth = new UsernamePasswordAuthenticationToken(
                email,
                null,
                authorities);
        SecurityContextHolder.getContext()
                .setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

}
