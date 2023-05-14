package kuznetsov.marketplace.server.service;

public interface CustomerPublisher {

    void publishCustomerRegistrationEvent(String customerEmail, String customerRole);

}
