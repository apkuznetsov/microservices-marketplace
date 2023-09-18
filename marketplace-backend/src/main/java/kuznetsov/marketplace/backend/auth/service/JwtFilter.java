package kuznetsov.marketplace.backend.auth.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuznetsov.marketplace.backend.users.dto.UserDto;
import kuznetsov.marketplace.backend.users.service.UserService;
import lombok.RequiredArgsConstructor;
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
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String token;
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            token = bearer.substring(7);
        } else {
            token = null;
        }

        if (token == null || token.isEmpty()
                || !jwtService.isValidAccessToken(token)) {
            return;
        }

        String email = jwtService.getEmailFromAccessToken(token);
        UserDto user = userService.getUserByEmailOrNull(email);
        if (user.isBanned() || !user.isEmailConfirmed()) {
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
