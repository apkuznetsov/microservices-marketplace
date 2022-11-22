package kuznetsov.marketplace.services.activation;

import kuznetsov.marketplace.services.activation.dto.ActivationRequest;

public interface ActivationService {

  String getActivationUrl();

  String generateActivationToken(ActivationRequest activationRequest);

}
