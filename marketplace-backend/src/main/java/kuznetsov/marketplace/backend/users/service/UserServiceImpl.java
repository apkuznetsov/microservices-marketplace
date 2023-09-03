package kuznetsov.marketplace.backend.users.service;

import kuznetsov.marketplace.backend.exception.service.ServiceException;
import kuznetsov.marketplace.backend.users.domain.Role;
import kuznetsov.marketplace.backend.users.domain.User;
import kuznetsov.marketplace.backend.users.dto.UserDto;
import kuznetsov.marketplace.backend.users.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserRepo repo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public @NotNull UserDto registerUser(@NotNull String email, @NotNull String password, @NotNull Role role) {
        User foundUser = repo
                .findByEmail(email)
                .orElse(null);
        if (foundUser != null) {
            throw new ServiceException(UserErrorCode.USER_ALREADY_EXISTS);
        }

        User newUser = mapper.toUser(email, role);
        newUser.setPassword(passwordEncoder.encode(password));
        User savedUser = repo.saveAndFlush(newUser);

        return mapper.toUserDto(savedUser);
    }

    @Override
    public UserDto getUserByEmailOrNull(@NotNull String email) {
        User foundUser = repo
                .findByEmail(email)
                .orElse(null);
        if (foundUser == null) {
            return null;
        }

        return mapper.toUserDto(foundUser);
    }

    @Override
    public void confirmEmail(@NotNull String email) {
        User foundUser = repo
                .findByEmail(email)
                .orElseThrow(() -> new ServiceException(UserErrorCode.USER_NOT_FOUND));

        if (foundUser.isEmailConfirmed()) {
            throw new ServiceException(UserErrorCode.USER_EMAIL_ALREADY_CONFIRMED);
        }

        foundUser.setEmailConfirmed(true);
        User updatedUser = repo.saveAndFlush(foundUser);

        mapper.toUserDto(updatedUser);
    }

    @Override
    public boolean doesUserPasswordMatch(@NotNull String email, @NotNull String password) {
        User foundUser = repo
                .findByEmail(email)
                .orElse(null);
        if (foundUser == null) {
            return false;
        }

        return passwordEncoder.matches(
                foundUser.getPassword(), password
        );
    }

}
