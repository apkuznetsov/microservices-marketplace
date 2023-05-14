package kuznetsov.marketplace.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
