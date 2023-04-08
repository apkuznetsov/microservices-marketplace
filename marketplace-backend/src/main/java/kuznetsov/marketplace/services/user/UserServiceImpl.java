package kuznetsov.marketplace.services.user;

import static kuznetsov.marketplace.services.user.UserErrorCode.USER_NOT_FOUND;

import kuznetsov.marketplace.database.user.UserRepository;
import kuznetsov.marketplace.models.user.User;
import kuznetsov.marketplace.services.exception.ServiceException;
import kuznetsov.marketplace.services.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
