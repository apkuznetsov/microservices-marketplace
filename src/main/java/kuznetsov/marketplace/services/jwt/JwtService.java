package kuznetsov.marketplace.services.jwt;

import kuznetsov.marketplace.services.auth.dto.UserCreateDto;

public interface JwtService {

  String createAccessToken(UserCreateDto authDto);

  boolean validateAccessToken(String token);

  String getEmailFromAccessToken(String token);

  String getRoleFromAccessToken(String token);

}
