package kuznetsov.marketplace.core.roles.customers.service;

public interface CustomerPublisher {

    void publishCustomerEventRegister(String customerEmail, String customerRole);

}
