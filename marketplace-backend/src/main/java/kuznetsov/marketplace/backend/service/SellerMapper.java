package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.domain.Seller;
import kuznetsov.marketplace.backend.dto.SellerDto;

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
