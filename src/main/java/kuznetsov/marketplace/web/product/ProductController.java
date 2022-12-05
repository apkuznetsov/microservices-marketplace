package kuznetsov.marketplace.web.product;

import java.net.URI;
import java.security.Principal;
import kuznetsov.marketplace.services.product.ProductService;
import kuznetsov.marketplace.services.product.dto.ProductDto;
import kuznetsov.marketplace.services.security.SellerPermission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

  public final String PRODUCT_URL = "/api/products";

  private final ProductService productService;

  @PostMapping(path = PRODUCT_URL)
  @SellerPermission
  public ResponseEntity<ProductDto> addProduct(
      @RequestBody ProductDto product, Principal principal) {

    String sellerEmail = principal.getName();
    log.info("The seller with {} email tries to add product {}.", sellerEmail, product.getTitle());
    ProductDto createdProduct = productService.addSellerProduct(sellerEmail, product);

    URI createdProductUri = URI.create(PRODUCT_URL + "/" + createdProduct.getId());
    return ResponseEntity.created(createdProductUri)
        .body(createdProduct);

  }

}
