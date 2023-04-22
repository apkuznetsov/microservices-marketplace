package kuznetsov.marketplace.backend.auth;

import kuznetsov.marketplace.backend.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String email;

    private UserRole role;

}
