package kuznetsov.marketplace.backend.service;

public interface PreorderParticipationPublisher {

    void publishCustomerParticipationEvent(String customerEmail, String customerRole);

}
