package kuznetsov.marketplace.backend.proxy;

public interface ActivationService {

    String getActivationUrl();

    String generateActivationToken(ActivationRequest activationRequest);

    ActivationMessage activate(String activationToken);

}
