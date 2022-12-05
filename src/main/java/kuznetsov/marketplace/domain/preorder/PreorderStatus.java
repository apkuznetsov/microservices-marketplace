package kuznetsov.marketplace.domain.preorder;

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
