package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.models.user.Seller;
import kuznetsov.marketplace.services.user.dto.SellerDto;

public interface SellerMapper {

  default Seller toSeller(SellerDto sellerDto) {
    return Seller.builder()
        .publicEmail(sellerDto.getEmail())
        .name(sellerDto.getName())
        .address(sellerDto.getAddress())
        .country(sellerDto.getCountry())
        .build();
  }

  default SellerDto toSellerDto(Seller seller) {
    return SellerDto.builder()
        .id(seller.getId())
        .email(seller.getPublicEmail())
        .name(seller.getName())
        .address(seller.getAddress())
        .country(seller.getCountry())
        .build();
  }

}
