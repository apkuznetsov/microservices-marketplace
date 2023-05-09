package kuznetsov.marketplace.server.web;

import kuznetsov.marketplace.server.dto.ProductCategoryDto;
import kuznetsov.marketplace.server.dto.ProductCategoryDtoPage;
import kuznetsov.marketplace.server.service.ModeratorPermission;
import kuznetsov.marketplace.server.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryController {

    public static final String CATEGORY_URL = "/api/v1/categories";

    private final ProductCategoryService categoryService;

    @PostMapping(path = CATEGORY_URL)
    @ModeratorPermission
    public ResponseEntity<ProductCategoryDto> addCategory(@RequestBody ProductCategoryDto category) {
        log.info("Moderator is trying  to add new category {}", category.getName());
        ProductCategoryDto addedCategory = categoryService.addCategory(category);

        URI addedCategoryUri = URI.create(CATEGORY_URL + "/" + addedCategory.getId());
        return ResponseEntity.created(addedCategoryUri).body(addedCategory);
    }

    @PutMapping(path = CATEGORY_URL + "/{id}")
    @ModeratorPermission
    public ResponseEntity<ProductCategoryDto> updateCategoryById(
            @PathVariable long id, @RequestBody ProductCategoryDto categoryDto) {

        log.info("Moderator is trying to update category {} id by new values {}",
                id, categoryDto.getName());
        ProductCategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);

        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping(path = CATEGORY_URL + "/{id}")
    public ResponseEntity<ProductCategoryDto> getCategoryById(@RequestParam long id) {
        log.info("Someone is trying to get category with {} id.", id);
        ProductCategoryDto category = categoryService.getCategoryById(id);

        return ResponseEntity.ok(category);
    }

    @GetMapping(path = CATEGORY_URL)
    public ResponseEntity<ProductCategoryDtoPage> getPagedCategories(
            @RequestParam(name = "page", required = false, defaultValue = "1") int pageNum) {

        log.info("Someone tries to get paged categories page number {}.", pageNum);
        ProductCategoryDtoPage pagedCategories = categoryService.getPagedCategories(pageNum);

        return ResponseEntity.ok(pagedCategories);
    }

}
