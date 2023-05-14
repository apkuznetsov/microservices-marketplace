package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.dto.CustomerDto;

public interface CustomerService {

    CustomerDto updateCustomerById(long customerId, CustomerDto customerDto);

    CustomerDto getCustomerById(long customerId);

}
