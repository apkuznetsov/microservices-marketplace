package kuznetsov.marketplace.settings.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettingDto {

    private Long id;

    private String name;

    private String coverUrl;

}
