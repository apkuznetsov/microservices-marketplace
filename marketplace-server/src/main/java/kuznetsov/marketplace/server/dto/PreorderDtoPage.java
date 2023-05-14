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
public class PreorderDtoPage {

    private Long totalPreorders;

    private Integer totalPreordersPages;

    private Integer preordersMaxPageSize;

    private Integer preordersPageNumber;

    private List<PreorderDto> preorders;

}
