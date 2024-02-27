package kuznetsov.marketplace.core.roles.sellers.service;

import kuznetsov.marketplace.core.roles.sellers.domain.Seller;
import kuznetsov.marketplace.core.roles.sellers.dto.SellerDto;

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
