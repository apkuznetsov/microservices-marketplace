package kuznetsov.marketplace.server.service;

public interface PreorderParticipationPublisher {

    void publishCustomerParticipationEvent(String customerEmail, String customerRole);

}
