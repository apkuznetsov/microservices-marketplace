package kuznetsov.marketplace.services.customer;

import static kuznetsov.marketplace.services.user.exception.UserErrorCode.USER_ALREADY_EXISTS;

import java.util.Optional;
import kuznetsov.marketplace.database.user.UserRepository;
import kuznetsov.marketplace.domain.user.User;
import kuznetsov.marketplace.services.customer.dto.CustomerDto;
import kuznetsov.marketplace.services.customer.mapper.CustomerMapper;
import kuznetsov.marketplace.services.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerMapper customerMapper;

  private final UserRepository userRepo;

  @Transactional
  public CustomerDto registerCustomer(String email, String password) {
    Optional<User> optUser = userRepo.findByEmail(email);
    if (optUser.isPresent()) {
      throw new ServiceException(USER_ALREADY_EXISTS);
    }

    User newUser = customerMapper.toCustomerUser(email, password);
    User savedUser = userRepo.saveAndFlush(newUser);

    return customerMapper.toCustomerDto(savedUser);
  }

}
