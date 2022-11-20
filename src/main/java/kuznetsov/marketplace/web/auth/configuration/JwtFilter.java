package kuznetsov.marketplace.web.auth.configuration;

import java.io.IOException;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kuznetsov.marketplace.services.auth.UserService;
import kuznetsov.marketplace.services.auth.dto.UserDto;
import kuznetsov.marketplace.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

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

    filterChain.doFilter(request, response);

    String token;
    String bearer = request.getHeader("Authorization");
    if (StringUtils.hasText(bearer)
        && bearer.startsWith("Bearer ")) {
      token = bearer.substring(7);
    } else {
      token = null;
    }

    if (token == null
        || token.isEmpty()
        || !jwtService.validateAccessToken(token)) {
      return;
    }

    UserDto user = userService
        .getUserByEmail(
            jwtService.getEmailFromAccessToken(token)
        );
    if (user.getIsBanned()
        || !user.getIsEmailConfirmed()) {
      return;
    }

    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(
                jwtService.getEmailFromAccessToken(token),
                null,
                Set.of(new SimpleGrantedAuthority(jwtService.getRoleFromAccessToken(token)))
            )
        );
  }

}
