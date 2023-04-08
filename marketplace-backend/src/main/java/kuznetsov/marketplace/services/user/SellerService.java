package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.services.user.dto.SellerDto;

public interface SellerService {

  SellerDto updateSellerById(long sellerId, SellerDto sellerDto);

  SellerDto getSellerById(long sellerId);

}
