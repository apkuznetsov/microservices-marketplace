package kuznetsov.marketplace.core.products.service;

public interface PreorderParticipationPublisher {

    void publishPreorderParticipationEvent(String customerEmail, String customerRole);

}
