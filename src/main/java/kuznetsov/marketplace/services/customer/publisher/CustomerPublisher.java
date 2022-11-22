package kuznetsov.marketplace.services.customer.publisher;

public interface CustomerPublisher {

  void publishCustomerRegistrationEvent(String customerEmail);

}
