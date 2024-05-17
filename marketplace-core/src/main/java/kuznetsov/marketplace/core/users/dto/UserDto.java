package kuznetsov.marketplace.core.users.dto;

import kuznetsov.marketplace.core.users.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String email;

    private Role role;

    private boolean isEmailConfirmed;

    private boolean isBanned;

}
