package kuznetsov.marketplace.services.preorder.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerParticipationEvent {

  private String email;

  private String role;

}
