package kuznetsov.marketplace.backend.service;

public interface CustomerPublisher {

    void publishCustomerRegistrationEvent(String customerEmail, String customerRole);

}
