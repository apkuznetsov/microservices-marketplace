package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.domain.User;
import kuznetsov.marketplace.server.dto.UserDto;
import kuznetsov.marketplace.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kuznetsov.marketplace.server.service.UserErrorCode.USER_NOT_FOUND;

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
