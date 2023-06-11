package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.dto.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidatorImpl implements CustomerValidator {

    @Override
    public void validateOrThrow(CustomerProperties customerProps, CustomerDto customerDto) {

    }

}
