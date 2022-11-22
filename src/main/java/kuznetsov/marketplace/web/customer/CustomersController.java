package kuznetsov.marketplace.web.customer;

import java.net.URI;
import kuznetsov.marketplace.services.customer.CustomerService;
import kuznetsov.marketplace.services.customer.dto.CustomerDto;
import kuznetsov.marketplace.services.customer.dto.CustomerRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomersController {

  public final String CUSTOMERS_URL = "/api/v1/customers";

  private final CustomerService customerService;

  @PostMapping(path = CUSTOMERS_URL)
  public ResponseEntity<CustomerDto> registerCustomer(
      @RequestBody CustomerRequest customerRequest) {

    log.info("The customer with {} email is trying to register as a customer.",
        customerRequest.getEmail());
    CustomerDto registeredCustomer = customerService.registerCustomer(
        customerRequest.getEmail(), customerRequest.getPassword());

    URI registeredCustomerUri = URI.create(CUSTOMERS_URL + "/" + registeredCustomer.getId());
    return ResponseEntity.created(registeredCustomerUri).body(registeredCustomer);
  }

}
