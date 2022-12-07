package kuznetsov.marketplace.services.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthDto {

  private Long id;

  private String email;

  private Boolean isBanned;

  private Boolean isEmailConfirmed;

}
