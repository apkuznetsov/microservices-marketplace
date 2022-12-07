package kuznetsov.marketplace.services.user;

import static kuznetsov.marketplace.services.user.CustomerErrorCode.CUSTOMER_NOT_FOUND;

import kuznetsov.marketplace.database.user.CustomerRepository;
import kuznetsov.marketplace.models.user.Customer;
import kuznetsov.marketplace.services.exception.ServiceException;
import kuznetsov.marketplace.services.user.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerMapper customerMapper;

  private final CustomerRepository customerRepo;

  @Override
  public CustomerDto getCustomerById(long customerId) {
    Customer customer = customerRepo.findById(customerId)
        .orElseThrow(() -> new ServiceException(CUSTOMER_NOT_FOUND));

    return customerMapper.toCustomerDto(customer);
  }

}
