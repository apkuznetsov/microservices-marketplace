package kuznetsov.marketplace.services.user;

import static kuznetsov.marketplace.services.user.UserErrorCode.USER_ALREADY_EXISTS;
import static kuznetsov.marketplace.services.user.UserErrorCode.USER_EMAIL_ALREADY_CONFIRMED;
import static kuznetsov.marketplace.services.user.UserErrorCode.USER_NOT_FOUND;

import java.util.Optional;
import kuznetsov.marketplace.database.user.CustomerRepository;
import kuznetsov.marketplace.database.user.UserRepository;
import kuznetsov.marketplace.models.user.Customer;
import kuznetsov.marketplace.models.user.User;
import kuznetsov.marketplace.services.exception.ServiceException;
import kuznetsov.marketplace.services.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;
  private final CustomerMapper customerMapper;
  private final CustomerPublisher customerPublisher;

  private final UserRepository userRepo;
  private final CustomerRepository customerRepo;

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

  @Override
  @Transactional
  public UserDto registerCustomer(String email, String password) {
    Optional<User> optUser = userRepo.findByEmail(email);
    if (optUser.isPresent()) {
      throw new ServiceException(USER_ALREADY_EXISTS);
    }

    User newUser = customerMapper.toCustomerUser(email, password);
    User savedUser = userRepo.saveAndFlush(newUser);

    Customer customer = customerMapper.toCustomer(savedUser);
    customerRepo.saveAndFlush(customer);

    customerPublisher.publishCustomerRegistrationEvent(
        savedUser.getEmail(), savedUser.getRole().name());
    return userMapper.toUserDto(savedUser);
  }

}
