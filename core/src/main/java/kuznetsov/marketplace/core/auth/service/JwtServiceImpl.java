package kuznetsov.marketplace.core.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final String accessSecret;
    private final Duration expirationAccessTime;

    public JwtServiceImpl(
            @Value("${auth.jwt-access-secret}")
            String accessSecretKey,
            @Value("${auth.jwt-access-expiration-time}")
            Duration expirationAccessTime) {

        this.accessSecret = Base64.getEncoder()
                .encodeToString(
                        accessSecretKey.getBytes(StandardCharsets.UTF_8));
        this.expirationAccessTime = expirationAccessTime;
    }

    public String generateAccessToken(String email, String role) {
        Claims claims = Jwts.claims();
        claims.setSubject(email);
        claims.put("role", role);

        Instant issuedAt = Instant.now();
        Instant expireAt = issuedAt.plus(expirationAccessTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expireAt))
                .signWith(SignatureAlgorithm.HS256, accessSecret)
                .compact();
    }

    public boolean isValidAccessToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(this.accessSecret)
                    .parseClaimsJws(token);

            return !claimsJws
                    .getBody()
                    .getExpiration()
                    .before(new Date());

        } catch (ExpiredJwtException e) {
            log.error("token expired exception", e);
        } catch (UnsupportedJwtException e) {
            log.error("unsupported jwt exception", e);
        } catch (MalformedJwtException e) {
            log.error("malformed jwt exception", e);
        } catch (SignatureException e) {
            log.error("invalid signature exception", e);
        } catch (Exception e) {
            log.error("token exception", e);
        }

        return false;
    }

    public String getEmailFromAccessToken(String token) {
        return Jwts.parser()
                .setSigningKey(accessSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getRoleFromAccessToken(String token) {
        return (String) Jwts.parser()
                .setSigningKey(accessSecret)
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }

}