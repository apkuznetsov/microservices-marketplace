package kuznetsov.marketplace.services.user;

import static kuznetsov.marketplace.services.user.UserErrorCode.USER_ALREADY_EXISTS;

import java.util.Optional;
import kuznetsov.marketplace.database.user.CustomerRepository;
import kuznetsov.marketplace.database.user.UserRepository;
import kuznetsov.marketplace.models.user.Customer;
import kuznetsov.marketplace.models.user.User;
import kuznetsov.marketplace.services.exception.ServiceException;
import kuznetsov.marketplace.services.user.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerMapper customerMapper;
  private final CustomerPublisher customerPublisher;

  private final CustomerRepository customerRepo;
  private final UserRepository userRepo;

  @Override
  @Transactional
  public CustomerDto registerCustomer(String email, String password) {
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
    return customerMapper.toCustomerDto(savedUser);
  }

}
