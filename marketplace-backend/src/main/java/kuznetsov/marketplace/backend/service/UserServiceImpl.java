package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.domain.User;
import kuznetsov.marketplace.backend.dto.UserDto;
import kuznetsov.marketplace.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kuznetsov.marketplace.backend.service.UserErrorCode.USER_NOT_FOUND;

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
