package kuznetsov.marketplace.services.activation;

import kuznetsov.marketplace.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivationServiceImpl implements ActivationService {

  private final JwtService jwtService;

  @Value("${activation.url}")
  private String activationUrl;

  @Override
  public String getActivationUrl() {
    return activationUrl;
  }

}
