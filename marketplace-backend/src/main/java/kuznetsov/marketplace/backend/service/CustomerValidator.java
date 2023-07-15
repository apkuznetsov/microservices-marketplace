package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.dto.CustomerDto;

public interface CustomerValidator {

    void validateOrThrow(CustomerProperties customerProps, CustomerDto customerDto);

}
