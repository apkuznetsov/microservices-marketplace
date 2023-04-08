package kuznetsov.marketplace.web.product;

import java.net.URI;
import java.security.Principal;
import kuznetsov.marketplace.services.product.ProductService;
import kuznetsov.marketplace.services.product.dto.ProductDto;
import kuznetsov.marketplace.services.product.dto.ProductDtoPage;
import kuznetsov.marketplace.services.security.SellerPermission;
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

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

  public static final String PRODUCT_URL = "/api/v1/products";
  public static final String PRODUCT_BY_CATEGORY = "/api/v1/categories";

  private final ProductService productService;

  @PostMapping(path = PRODUCT_URL)
  @SellerPermission
  public ResponseEntity<ProductDto> addProduct(
      @RequestBody ProductDto product,
      Principal principal) {

    String sellerEmail = principal.getName();
    log.info("The seller with {} email is trying to add product {}.", sellerEmail,
        product.getTitle());
    ProductDto createdProduct = productService.addSellerProduct(sellerEmail, product);

    URI createdProductUri = URI.create(PRODUCT_URL + "/" + createdProduct.getId());
    return ResponseEntity.created(createdProductUri)
        .body(createdProduct);

  }

  @PutMapping(path = PRODUCT_URL + "/{id}")
  @SellerPermission
  public ResponseEntity<ProductDto> updateProductById(
      @PathVariable Long id,
      @RequestBody ProductDto product,
      Principal principal) {

    String sellerEmail = principal.getName();
    log.info("The seller with {} email is trying to update product {}.",
        sellerEmail, product.getTitle());
    ProductDto updatedProduct = productService.updateSellerProductById(sellerEmail, id, product);

    return ResponseEntity.ok(updatedProduct);
  }

  @GetMapping(path = PRODUCT_URL + "/{id}")
  public ResponseEntity<ProductDto> getProductById(@RequestParam long id) {
    log.info("Someone is trying to get product with {} id.", id);
    ProductDto product = productService.getProductById(id);

    return ResponseEntity.ok(product);
  }

  @GetMapping(path = PRODUCT_BY_CATEGORY + "/{id}" + "/products")
  public ResponseEntity<ProductDtoPage> getPagedProductsByCategoryId(
      @PathVariable long id,
      @RequestParam(name = "page", required = false, defaultValue = "1") int pageNum) {

    log.info("Someone is trying to get paged products  {} by category {} id.", pageNum, id);
    ProductDtoPage pagedProducts = productService.getPagedProductsByCategoryId(id, pageNum);

    return ResponseEntity.ok(pagedProducts);
  }

}
