package kuznetsov.marketplace.server.domain;

public enum PreorderParticipationStatus {

    SELLER("SELLER"),

    CLIENT_AWAITING_PAYMENT("CLIENT_AWAITING_PAYMENT");

    private final String value;

    PreorderParticipationStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
