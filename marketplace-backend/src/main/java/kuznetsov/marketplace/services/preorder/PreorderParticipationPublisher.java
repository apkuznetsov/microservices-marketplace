package kuznetsov.marketplace.services.preorder;

public interface PreorderParticipationPublisher {

  void publishCustomerParticipationEvent(String customerEmail, String customerRole);

}
