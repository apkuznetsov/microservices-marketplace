package kuznetsov.marketplace.services.activation;

import kuznetsov.marketplace.services.activation.dto.ActivationMessage;
import kuznetsov.marketplace.services.activation.dto.ActivationRequest;
import kuznetsov.marketplace.services.jwt.JwtService;
import kuznetsov.marketplace.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivationServiceImpl implements ActivationService {

  private final ActivationProperties activationProps;

  private final JwtService jwtService;
  private final UserService userService;

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

  @Override
  public ActivationMessage activate(String activationToken) {
    String userEmail = jwtService.getEmailFromAccessToken(activationToken);
    userService.confirmUserEmail(userEmail);
    return new ActivationMessage("Your account has been successfully activated");
  }

}
