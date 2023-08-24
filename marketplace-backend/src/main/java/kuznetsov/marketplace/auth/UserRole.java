package kuznetsov.marketplace.auth;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    SELLER, CUSTOMER;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

}
