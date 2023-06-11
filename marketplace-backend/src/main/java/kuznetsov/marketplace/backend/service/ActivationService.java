package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.dto.ActivationMessage;
import kuznetsov.marketplace.backend.dto.ActivationRequest;

public interface ActivationService {

    String getActivationUrl();

    String generateActivationToken(ActivationRequest activationRequest);

    ActivationMessage activate(String activationToken);

}
