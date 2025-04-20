package kuznetsov.marketplace.settings;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryController {

    public static final String CATEGORY_URL = "/api/v1/categories";

    private final ProductCategoryService categoryService;

    @GetMapping(path = CATEGORY_URL + "/{id}")
    public ProductCategoryDto getCategoryById(@PathVariable Long id) {
        log.info("Someone is trying to get category with {} id.", id);
        return categoryService
                .findById(id)
                .orElseThrow();
    }

}
