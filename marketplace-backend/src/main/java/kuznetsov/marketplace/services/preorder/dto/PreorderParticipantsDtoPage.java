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
public class PreorderParticipantsDtoPage {

  private Long totalPreorderParticipants;

  private Integer totalPreorderParticipantsPages;

  private Integer preorderParticipantsMaxPageSize;

  private Integer preorderParticipantsPageNumber;

  private Long preorderId;

  private List<PreorderParticipantDto> preorderParticipants;

}
