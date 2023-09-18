package kuznetsov.marketplace.backend.roles.customers.service;

import kuznetsov.marketplace.backend.roles.customers.dto.CustomerDto;

public interface CustomerValidator {

    void validateCustomerOrThrow(CustomerProperties customerProps, CustomerDto customerDto);

}
