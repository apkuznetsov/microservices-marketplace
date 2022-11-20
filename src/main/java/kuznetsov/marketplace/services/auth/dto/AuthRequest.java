package kuznetsov.marketplace.services.auth.dto;

import kuznetsov.marketplace.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequest {

  private String email;

  private String password;

  private UserRole role;

}
