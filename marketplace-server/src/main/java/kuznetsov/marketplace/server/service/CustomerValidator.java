package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.dto.CustomerDto;

public interface CustomerValidator {

    void validateOrThrow(CustomerProperties customerProps, CustomerDto customerDto);

}
