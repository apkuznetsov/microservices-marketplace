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

  private final CustomerProperties customerProperties;
  private final CustomerMapper customerMapper;
  private final CustomerValidator customerValidator;

  private final CustomerRepository customerRepo;

  @Override
  public CustomerDto updateCustomerById(long customerId, CustomerDto customerDto) {
    customerValidator.validateOrThrow(customerProperties, customerDto);

    if (!customerRepo.existsById(customerId)) {
      throw new ServiceException(CUSTOMER_NOT_FOUND);
    }

    Customer newCustomer = customerMapper.toCustomer(customerDto);
    newCustomer.setId(customerId);
    Customer updatedCustomer = customerRepo.saveAndFlush(newCustomer);

    return customerMapper.toCustomerDto(updatedCustomer);
  }

  @Override
  public CustomerDto getCustomerById(long customerId) {
    Customer customer = customerRepo.findById(customerId)
        .orElseThrow(() -> new ServiceException(CUSTOMER_NOT_FOUND));

    return customerMapper.toCustomerDto(customer);
  }

}
