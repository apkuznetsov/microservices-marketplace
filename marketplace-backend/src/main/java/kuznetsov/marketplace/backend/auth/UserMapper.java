package kuznetsov.marketplace.backend.auth;

public interface UserMapper {

    default UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

}
