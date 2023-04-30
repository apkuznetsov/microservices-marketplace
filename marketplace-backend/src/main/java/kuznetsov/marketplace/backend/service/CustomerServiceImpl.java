package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.domain.Customer;
import kuznetsov.marketplace.backend.dto.CustomerDto;
import kuznetsov.marketplace.backend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kuznetsov.marketplace.backend.service.CustomerErrorCode.CUSTOMER_NOT_FOUND;

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
