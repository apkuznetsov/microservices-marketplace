package kuznetsov.marketplace.services.user;

import static kuznetsov.marketplace.services.user.UserErrorCode.USER_EMAIL_ALREADY_CONFIRMED;
import static kuznetsov.marketplace.services.user.UserErrorCode.USER_NOT_FOUND;

import kuznetsov.marketplace.database.user.UserRepository;
import kuznetsov.marketplace.domain.user.User;
import kuznetsov.marketplace.services.exception.ServiceException;
import kuznetsov.marketplace.services.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;

  private final UserRepository userRepo;

  @Override
  @Transactional(readOnly = true)
  public UserDto getUserByEmail(String userEmail) {
    User user = userRepo
        .findByEmail(userEmail)
        .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));

    return userMapper.toUserDto(user);
  }

  @Override
  public UserDto confirmUserEmail(String userEmail) {
    User user = userRepo
        .findByEmail(userEmail)
        .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));

    if (user.isEmailConfirmed()) {
      throw new ServiceException(USER_EMAIL_ALREADY_CONFIRMED);
    }

    user.setEmailConfirmed(true);
    User updatedUser = userRepo.saveAndFlush(user);

    return userMapper.toUserDto(updatedUser);
  }

}
