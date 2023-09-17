package kuznetsov.marketplace.backend.roles.customers.web;

import kuznetsov.marketplace.backend.roles.customers.dto.CustomerDto;
import kuznetsov.marketplace.backend.roles.customers.dto.CustomerRequestRegister;
import kuznetsov.marketplace.backend.roles.customers.service.CustomerPermission;
import kuznetsov.marketplace.backend.roles.customers.service.CustomerService;
import kuznetsov.marketplace.backend.users.dto.UserDto;
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

    @PostMapping(path = CUSTOMER_URL)
    public ResponseEntity<UserDto> registerCustomer(@RequestBody CustomerRequestRegister request) {
        log.info("The customer with {} email is trying to register as a customer.", request.getEmail());
        UserDto registeredCustomer = customerService.registerCustomer(
                request.getEmail(), request.getPassword());

        URI uri = URI.create(CUSTOMER_URL + "/" + registeredCustomer.getId());
        return ResponseEntity
                .created(uri)
                .body(registeredCustomer);
    }

    @GetMapping(path = CUSTOMER_URL + "/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable long id) {
        log.info("Someone is trying to get customer with {} id.", id);
        CustomerDto customer = customerService.getCustomerById(id);

        return ResponseEntity.ok(customer);
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

}
