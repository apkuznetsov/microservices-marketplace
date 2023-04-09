package kuznetsov.marketplace.backend.auth;

import kuznetsov.marketplace.backend.exception.ServiceException;
import kuznetsov.marketplace.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kuznetsov.marketplace.backend.auth.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepo;

    public UserDto getUserByEmail(String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));

        return userMapper.toUserDto(user);
    }

}
