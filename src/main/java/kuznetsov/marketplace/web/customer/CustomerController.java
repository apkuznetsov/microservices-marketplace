package kuznetsov.marketplace.web.customer;

import java.net.URI;
import kuznetsov.marketplace.services.user.UserService;
import kuznetsov.marketplace.services.user.dto.CustomerRequest;
import kuznetsov.marketplace.services.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

  public final String CUSTOMERS_URL = "/api/v1/customers";

  private final UserService userService;

  @PostMapping(path = CUSTOMERS_URL)
  public ResponseEntity<UserDto> registerCustomer(
      @RequestBody CustomerRequest customerRequest) {

    log.info("The customer with {} email is trying to register as a customer.",
        customerRequest.getEmail());
    UserDto registeredCustomer = userService.registerCustomer(
        customerRequest.getEmail(), customerRequest.getPassword());

    URI registeredCustomerUri = URI.create(CUSTOMERS_URL + "/" + registeredCustomer.getId());
    return ResponseEntity.created(registeredCustomerUri).body(registeredCustomer);
  }

}
