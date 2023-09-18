package kuznetsov.marketplace.backend.products.dto;

import jakarta.annotation.PostConstruct;
import kuznetsov.marketplace.backend.products.domain.PreorderParticipationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
