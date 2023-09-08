package kuznetsov.marketplace.backend.users.dto;

import kuznetsov.marketplace.backend.users.domain.Role;
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
