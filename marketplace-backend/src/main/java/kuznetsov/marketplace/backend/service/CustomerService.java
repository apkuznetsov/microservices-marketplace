package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.dto.CustomerDto;

public interface CustomerService {

    CustomerDto updateCustomerById(long customerId, CustomerDto customerDto);

    CustomerDto getCustomerById(long customerId);

}
