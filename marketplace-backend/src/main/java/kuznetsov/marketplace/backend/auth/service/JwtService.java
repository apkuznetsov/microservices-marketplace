package kuznetsov.marketplace.backend.auth.service;

public interface JwtService {

    String generateAccessToken(String email, String role);

    boolean isValidAccessToken(String token);

    String getEmailFromAccessToken(String token);

    String getRoleFromAccessToken(String token);

}
