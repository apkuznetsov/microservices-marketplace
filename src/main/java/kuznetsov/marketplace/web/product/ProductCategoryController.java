package kuznetsov.marketplace.web.product;

import kuznetsov.marketplace.services.product.ProductCategoryService;
import kuznetsov.marketplace.services.product.dto.ProductCategoryDtoPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryController {

  public final String CATEGORY_URL = "/api/categories";

  private final ProductCategoryService categoryService;

  @GetMapping(path = CATEGORY_URL)
  public ResponseEntity<ProductCategoryDtoPage> getPagedCategories(
      @RequestParam(name = "page", required = false, defaultValue = "1") int pageNum) {

    log.info("Someone tries to get paged categories page number {}.", pageNum);
    ProductCategoryDtoPage pagedCategories = categoryService.getPagedCategories(pageNum);

    return ResponseEntity.ok(pagedCategories);
  }

}
