package kuznetsov.marketplace.backend.domain;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    ADMIN, MODERATOR, SELLER, CUSTOMER;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

}