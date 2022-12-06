package kuznetsov.marketplace.services.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerDto {

  private String name;

  private String address;

  private String country;

  private String email;

}
