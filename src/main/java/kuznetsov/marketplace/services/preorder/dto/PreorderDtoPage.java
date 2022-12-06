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
public class PreorderDtoPage {

  private Long totalPreorders;

  private Integer totalPreordersPages;

  private Integer preordersMaxPageSize;

  private Integer preordersPageNumber;

  private List<PreorderDto> preorders;

}
