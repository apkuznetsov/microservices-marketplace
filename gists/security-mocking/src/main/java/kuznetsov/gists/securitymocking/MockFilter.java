package kuznetsov.gists.securitymocking;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Profile("test")
@Component
@RequiredArgsConstructor
class MockFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final SomeUserService someUserService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {

        String email = extractEmailOrNull(request);
        if (email == null) {
            chain.doFilter(request, response);
            return;
        }

        if (someUserService.findUserOrNullByEmail(email) == null) {
            response.setStatus(401);
            return;
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(
                email,
                email,
                Collections.singleton((GrantedAuthority) () -> "USER")
        );

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);

        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) {
        return false;
    }

    private String extractEmailOrNull(@NotNull HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        return bearer.startsWith(BEARER_PREFIX)
                ? bearer.substring(BEARER_PREFIX.length())
                : null;
    }

}
