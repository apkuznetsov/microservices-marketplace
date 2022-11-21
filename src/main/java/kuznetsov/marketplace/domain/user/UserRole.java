package kuznetsov.marketplace.domain.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

  ADMIN, MODERATOR, SELLER, CUSTOMER;

  @Override
  public String getAuthority() {
    return "ROLE_" + name();
  }

}
