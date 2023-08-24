package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.auth.User;
import kuznetsov.marketplace.auth.UserRepo;
import kuznetsov.marketplace.backend.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kuznetsov.marketplace.backend.service.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepo userRepo;

    public UserDto getUserByEmail(String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));

        return userMapper.toUserDto(user);
    }

}
