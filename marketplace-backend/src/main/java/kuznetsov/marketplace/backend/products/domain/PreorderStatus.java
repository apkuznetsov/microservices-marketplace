package kuznetsov.marketplace.backend.products.domain;

public enum PreorderStatus {

    STARTED("STARTED");

    private final String value;

    PreorderStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
