package kuznetsov.marketplace.core.roles.customers.service;

import kuznetsov.marketplace.core.roles.customers.dto.CustomerDto;

public interface CustomerValidator {

    void validateCustomerOrThrow(CustomerProperties customerProps, CustomerDto customerDto);

}
