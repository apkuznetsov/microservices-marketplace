package kuznetsov.marketplace.backend.auth.service;

import kuznetsov.marketplace.backend.auth.dto.ActivationRequest;
import kuznetsov.marketplace.backend.auth.dto.ActivationResponse;

public interface ActivationService {

    String getActivationUrl();

    String generateActivationToken(ActivationRequest activationRequest);

    ActivationResponse activate(String activationToken);

}
