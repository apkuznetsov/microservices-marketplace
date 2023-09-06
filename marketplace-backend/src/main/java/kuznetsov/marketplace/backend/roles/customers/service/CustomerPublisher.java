package kuznetsov.marketplace.backend.roles.customers.service;

public interface CustomerPublisher {

    void publishCustomerEventRegister(String customerEmail, String customerRole);

}
