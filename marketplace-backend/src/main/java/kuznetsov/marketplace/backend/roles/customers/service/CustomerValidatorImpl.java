package kuznetsov.marketplace.backend.roles.customers.service;

import kuznetsov.marketplace.backend.roles.customers.dto.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidatorImpl implements CustomerValidator {

    @Override
    public void validateCustomerOrThrow(CustomerProperties customerProps, CustomerDto customerDto) {
        // TODO
    }

}
