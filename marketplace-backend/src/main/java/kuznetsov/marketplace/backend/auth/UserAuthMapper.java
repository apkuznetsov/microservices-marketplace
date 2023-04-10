package kuznetsov.marketplace.backend.auth;

import kuznetsov.marketplace.backend.domain.UserRole;

public interface UserAuthMapper {

    default User toCustomerUser(String email) {
        return User.builder()
                .email(email)
                .role(UserRole.CUSTOMER)
                .isEmailConfirmed(false)
                .isBanned(false)
                .build();
    }

    default UserAuthDto toUserDto(User user) {
        return UserAuthDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isEmailConfirmed(user.isEmailConfirmed())
                .isBanned(user.isBanned())
                .build();
    }

}
