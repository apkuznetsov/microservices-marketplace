package kuznetsov.marketplace.backend.products.service;

public interface PreorderParticipationPublisher {

    void publishPreorderParticipationEvent(String customerEmail, String customerRole);

}
