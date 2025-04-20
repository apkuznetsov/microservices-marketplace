package kuznetsov.marketplace.settings.web;

import kuznetsov.marketplace.settings.dto.SettingDto;
import kuznetsov.marketplace.settings.exception.SettingNotFoundException;
import kuznetsov.marketplace.settings.service.SettingServiceStarter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SettingControllerStarter {

    public static final String SETTING_STARTER_URL = "/settings-starter/v1/settings";

    private final SettingServiceStarter settingService;

    @GetMapping(path = SETTING_STARTER_URL + "/{id}")
    public SettingDto getSettingById(@PathVariable Long id) {
        log.info("Someone is trying to get setting with {} id.", id);
        return settingService.findById(id)
                .orElseThrow(SettingNotFoundException::new);
    }

    @ExceptionHandler(SettingNotFoundException.class)
    public String handleNotFound(SettingNotFoundException e) {
        return "setting not found";
    }

}
