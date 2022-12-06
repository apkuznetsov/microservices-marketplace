package kuznetsov.marketplace.services.user.dto;

import kuznetsov.marketplace.models.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {

  private Long id;

  private String email;

  private UserRole role;

}
