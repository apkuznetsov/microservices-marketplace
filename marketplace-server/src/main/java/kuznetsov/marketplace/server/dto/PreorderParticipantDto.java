package kuznetsov.marketplace.server.dto;

import kuznetsov.marketplace.server.domain.PreorderParticipationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreorderParticipantDto {

    private String email;

    private String surname;

    private String name;

    private String address;

    private Integer preorderedQuantity;

    private PreorderParticipationStatus participationStatus;

    @PostConstruct
    private void init() {
        preorderedQuantity = 1;
    }

}
