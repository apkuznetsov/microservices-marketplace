package kuznetsov.marketplace.services.customer;

public interface CustomerPublisher {

  void publishCustomerRegistrationEvent(String customerEmail);

}
