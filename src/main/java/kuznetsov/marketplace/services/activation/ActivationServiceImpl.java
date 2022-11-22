package kuznetsov.marketplace.services.activation;

import kuznetsov.marketplace.services.activation.dto.ActivationRequest;
import kuznetsov.marketplace.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivationServiceImpl implements ActivationService {

  private final ActivationProperties activationProps;

  private final JwtService jwtService;


  @Override
  public String getActivationUrl() {
    return activationProps.getUrl();
  }

  @Override
  public String generateActivationToken(ActivationRequest activationRequest) {
    return jwtService.generateAccessToken(
        activationRequest.getEmail(), activationRequest.getRole()
    );
  }

}
