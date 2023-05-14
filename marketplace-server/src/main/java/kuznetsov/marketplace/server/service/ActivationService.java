package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.dto.ActivationMessage;
import kuznetsov.marketplace.server.dto.ActivationRequest;

public interface ActivationService {

    String getActivationUrl();

    String generateActivationToken(ActivationRequest activationRequest);

    ActivationMessage activate(String activationToken);

}
