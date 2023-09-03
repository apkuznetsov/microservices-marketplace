package kuznetsov.marketplace.backend.users.service;

import kuznetsov.marketplace.backend.users.domain.Role;
import kuznetsov.marketplace.backend.users.dto.UserDto;
import org.jetbrains.annotations.NotNull;

public interface UserService {

    @NotNull UserDto registerUser(@NotNull String email, @NotNull String password, @NotNull Role role);

    UserDto getUserByEmailOrNull(@NotNull String email);

    void confirmEmail(@NotNull String email);

    boolean doesUserPasswordMatch(@NotNull String email, @NotNull String passwordNotHashed);
    
}
