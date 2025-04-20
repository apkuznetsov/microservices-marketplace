package kuznetsov.marketplace.core.web;

import kuznetsov.marketplace.settings.dto.SettingDto;
import kuznetsov.marketplace.settings.service.SettingServiceStarter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = DebugController.DEBUG)
@RequiredArgsConstructor
@Slf4j
public class DebugController {

    public static final String DEBUG = "/debug";

    private final SettingServiceStarter settingService;

    @GetMapping
    public List<SettingDto> getSettings() {
        log.info("Someone is trying to get all settings.");
        return settingService.findAll();
    }

}
