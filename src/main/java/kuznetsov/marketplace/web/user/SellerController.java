package kuznetsov.marketplace.web.user;

import java.security.Principal;
import kuznetsov.marketplace.services.security.SellerPermission;
import kuznetsov.marketplace.services.user.SellerService;
import kuznetsov.marketplace.services.user.dto.SellerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SellerController {

  public final String SELLER_URL = "/api/v1/sellers";

  private final SellerService sellerService;

  @PutMapping(path = SELLER_URL + "/{id}")
  @SellerPermission
  public ResponseEntity<SellerDto> updateSellerById(
      @PathVariable long id, @RequestBody SellerDto seller, Principal principal) {

    String sellerEmail = principal.getName();
    log.info("Seller {} is trying to update profile {} id by new values {}",
        sellerEmail, id, seller.getName());
    SellerDto updatedSeller = sellerService.updateSellerById(id, seller);

    return ResponseEntity.ok(updatedSeller);
  }

  @GetMapping(path = SELLER_URL + "/{id}")
  public ResponseEntity<SellerDto> getSellerById(long id) {
    log.info("Someone is trying to get seller with {} id.", id);
    SellerDto seller = sellerService.getSellerById(id);

    return ResponseEntity.ok().body(seller);
  }

}
