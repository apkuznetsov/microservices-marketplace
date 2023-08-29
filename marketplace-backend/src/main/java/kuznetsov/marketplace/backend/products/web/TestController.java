package kuznetsov.marketplace.backend.products.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @PostMapping("/test")
    @CrossOrigin("http://localhost:8080")
    public String test() {
        log.info("test method called");
        return "HELLO";
    }

}
