package kuznetsov.marketplace.backend.web;

import kuznetsov.marketplace.backend.auth.UserAuthDto;
import kuznetsov.marketplace.backend.auth.UserAuthService;
import kuznetsov.marketplace.backend.dto.CustomerDto;
import kuznetsov.marketplace.backend.dto.CustomerRequest;
import kuznetsov.marketplace.backend.service.CustomerPermission;
import kuznetsov.marketplace.backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    public static final String CUSTOMER_URL = "/api/v1/customers";

    private final CustomerService customerService;
    private final UserAuthService userAuthService;

    @PostMapping(path = CUSTOMER_URL)
    public ResponseEntity<UserAuthDto> registerCustomer(
            @RequestBody CustomerRequest customerRequest) {

        log.info("The customer with {} email is trying to register as a customer.",
                customerRequest.getEmail());
        UserAuthDto registeredCustomer = userAuthService.registerCustomer(
                customerRequest.getEmail(), customerRequest.getPassword());

        URI registeredCustomerUri = URI.create(CUSTOMER_URL + "/" + registeredCustomer.getId());
        return ResponseEntity.created(registeredCustomerUri).body(registeredCustomer);
    }

    @PutMapping(path = CUSTOMER_URL + "/{id}")
    @CustomerPermission
    public ResponseEntity<CustomerDto> updateCustomerById(
            @PathVariable long id, @RequestBody CustomerDto customer, Principal principal) {

        String customerEmail = principal.getName();
        log.info("Customer {} is trying to update profile {} id by new values {}",
                customerEmail, id, customer.getName());
        CustomerDto updatedCustomer = customerService.updateCustomerById(id, customer);

        return ResponseEntity.ok(updatedCustomer);
    }

    @GetMapping(path = CUSTOMER_URL + "/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(long id) {
        log.info("Someone is trying to get customer with {} id.", id);
        CustomerDto customer = customerService.getCustomerById(id);

        return ResponseEntity.ok().body(customer);
    }

}
