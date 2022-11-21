package kuznetsov.marketplace.services.user;

import static kuznetsov.marketplace.services.user.exception.UserErrorCode.USER_ALREADY_EXISTS;
import static kuznetsov.marketplace.services.user.exception.UserErrorCode.USER_NOT_FOUND;

import java.util.Optional;
import kuznetsov.marketplace.database.user.UserRepository;
import kuznetsov.marketplace.domain.user.User;
import kuznetsov.marketplace.services.exception.ServiceException;
import kuznetsov.marketplace.services.user.dto.UserDto;
import kuznetsov.marketplace.services.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceDefault implements UserService {

  private final UserMapper userMapper;

  private final UserRepository userRepo;

  @Transactional
  public UserDto registerCustomer(String email, String password) {
    Optional<User> optUser = userRepo.findByEmail(email);
    if (optUser.isPresent()) {
      throw new ServiceException(USER_ALREADY_EXISTS);
    }

    User newUser = userMapper.toCustomerUser(email, password);
    User savedUser = userRepo.saveAndFlush(newUser);

    return userMapper.toUserDto(savedUser);
  }

  @Transactional(readOnly = true)
  public UserDto getUserByEmail(String email) {
    User user = userRepo
        .findByEmail(email)
        .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));

    return userMapper.toUserDto(user);
  }

}
