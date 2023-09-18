package kuznetsov.marketplace.backend.roles.customers.service;

import kuznetsov.marketplace.backend.exception.service.ServiceException;
import kuznetsov.marketplace.backend.roles.customers.domain.Customer;
import kuznetsov.marketplace.backend.roles.customers.dto.CustomerDto;
import kuznetsov.marketplace.backend.roles.customers.repos.CustomerRepo;
import kuznetsov.marketplace.backend.users.domain.Role;
import kuznetsov.marketplace.backend.users.dto.UserDto;
import kuznetsov.marketplace.backend.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kuznetsov.marketplace.backend.roles.customers.service.CustomerErrorCode.CUSTOMER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerProperties props;
    private final CustomerMapper mapper;
    private final CustomerValidator validator;
    private final CustomerRepo repo;
    private final CustomerPublisher publisher;

    private final UserService userService;

    @Override
    public UserDto registerCustomer(String email, String password) {
        UserDto customerAsUser = userService
                .registerUser(email, password, Role.CUSTOMER);

        Customer customer = mapper.toCustomer(customerAsUser);
        repo.saveAndFlush(customer);

        publisher.publishCustomerEventRegister(
                customerAsUser.getEmail(), customerAsUser.getRole().name()
        );
        return customerAsUser;
    }

    @Override
    public CustomerDto getCustomerById(long customerId) {
        Customer foundCustomer = repo
                .findById(customerId)
                .orElseThrow(() -> new ServiceException(CUSTOMER_NOT_FOUND));

        return mapper.toCustomerDto(foundCustomer);
    }

    @Override
    public CustomerDto updateCustomerById(long customerId, CustomerDto customerDto) {
        validator.validateCustomerOrThrow(props, customerDto);

        if (!repo.existsById(customerId)) {
            throw new ServiceException(CUSTOMER_NOT_FOUND);
        }

        Customer newCustomer = mapper.toCustomer(customerDto);
        newCustomer.setId(customerId);
        Customer updatedCustomer = repo.saveAndFlush(newCustomer);

        return mapper.toCustomerDto(updatedCustomer);
    }

}
