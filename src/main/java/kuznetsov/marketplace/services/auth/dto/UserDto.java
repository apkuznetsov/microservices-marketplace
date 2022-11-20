package kuznetsov.marketplace.services.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

  private String email;

  private Boolean isBanned;

  private Boolean isEmailConfirmed;

}
