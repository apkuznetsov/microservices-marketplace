package kuznetsov.marketplace.services.auth;

import static kuznetsov.marketplace.services.auth.exception.UserErrorCode.USER_NOT_FOUND;

import kuznetsov.marketplace.database.auth.UserRepository;
import kuznetsov.marketplace.domain.auth.User;
import kuznetsov.marketplace.services.auth.dto.UserDto;
import kuznetsov.marketplace.services.auth.mapper.UserMapper;
import kuznetsov.marketplace.services.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceDefault implements UserService {

  private final UserMapper userMapper;

  private final UserRepository userRepo;

  @Transactional(readOnly = true)
  public UserDto getUserByEmail(String email) {
    User user = userRepo
        .findByEmail(email)
        .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));

    return userMapper.toUserDto(user);
  }

}
