package kuznetsov.marketplace.services.auth;

import kuznetsov.marketplace.services.auth.dto.UserCreateDto;

public interface JwtProvider {

  String createAccessToken(UserCreateDto authDto);

  boolean validateAccessToken(String token);

  String getEmailFromAccessToken(String token);

  String getRoleFromAccessToken(String token);

}
