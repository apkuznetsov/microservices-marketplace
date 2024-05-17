package kuznetsov.marketplace.core.auth.service;

import kuznetsov.marketplace.core.auth.dto.ActivationRequest;
import kuznetsov.marketplace.core.auth.dto.ActivationResponse;

public interface ActivationService {

    String getActivationUrl();

    String generateActivationToken(ActivationRequest activationRequest);

    ActivationResponse activate(String activationToken);

}
