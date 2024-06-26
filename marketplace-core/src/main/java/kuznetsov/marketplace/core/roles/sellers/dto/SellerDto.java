package kuznetsov.marketplace.core.roles.sellers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerDto {

    private Long id;

    private String name;

    private String address;

    private String country;

    private String email;

}
