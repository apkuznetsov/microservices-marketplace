package kuznetsov.marketplace.web.user;

import java.net.URI;
import kuznetsov.marketplace.services.user.UserAuthService;
import kuznetsov.marketplace.services.user.dto.CustomerRequest;
import kuznetsov.marketplace.services.user.dto.UserAuthDto;
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

  private final UserAuthService userAuthService;

  @PostMapping(path = CUSTOMERS_URL)
  public ResponseEntity<UserAuthDto> registerCustomer(
      @RequestBody CustomerRequest customerRequest) {

    log.info("The customer with {} email is trying to register as a customer.",
        customerRequest.getEmail());
    UserAuthDto registeredCustomer = userAuthService.registerCustomer(
        customerRequest.getEmail(), customerRequest.getPassword());

    URI registeredCustomerUri = URI.create(CUSTOMERS_URL + "/" + registeredCustomer.getId());
    return ResponseEntity.created(registeredCustomerUri).body(registeredCustomer);
  }

}
