package kuznetsov.marketplace.services.user;

public interface CustomerPublisher {

  void publishCustomerRegistrationEvent(String customerEmail, String customerRole);

}
