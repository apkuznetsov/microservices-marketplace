package kuznetsov.marketplace.backend.users.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    UNKNOWN, SELLER, CUSTOMER;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

}
