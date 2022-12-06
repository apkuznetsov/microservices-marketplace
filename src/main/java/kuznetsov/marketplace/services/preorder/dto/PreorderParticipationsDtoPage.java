package kuznetsov.marketplace.services.preorder.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreorderParticipationsDtoPage {

  private Long totalPreorderParticipations;

  private Integer totalPreorderParticipationsPages;

  private Integer preorderParticipationsMaxPageSize;

  private Integer preorderParticipationsPageNumber;

  private Long preorderId;

  private List<PreorderParticipantDto> preorderParticipations;

}
