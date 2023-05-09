package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.dto.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidatorImpl implements CustomerValidator {

    @Override
    public void validateOrThrow(CustomerProperties customerProps, CustomerDto customerDto) {

    }

}
