package kuznetsov.marketplace.backend.auth.service;

import kuznetsov.marketplace.backend.auth.dto.ActivationRequest;
import kuznetsov.marketplace.backend.auth.dto.ActivationResponse;
import kuznetsov.marketplace.backend.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivationServiceImpl implements ActivationService {

    private final ActivationProperties props;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public String getActivationUrl() {
        return props.getUrl();
    }

    @Override
    public String generateActivationToken(ActivationRequest activationRequest) {
        return jwtService.generateAccessToken(
                activationRequest.getEmail(), activationRequest.getRole()
        );
    }

    @Override
    public ActivationResponse activate(String activationToken) {
        String userEmail = jwtService.getEmailFromAccessToken(activationToken);
        userService.confirmEmail(userEmail);
        return new ActivationResponse("your account has been successfully activated");
    }

}
