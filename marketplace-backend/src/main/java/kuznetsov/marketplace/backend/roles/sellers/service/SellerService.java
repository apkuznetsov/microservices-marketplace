package kuznetsov.marketplace.backend.roles.sellers.service;

import kuznetsov.marketplace.backend.roles.sellers.dto.SellerDto;

public interface SellerService {

    SellerDto updateSellerById(long sellerId, SellerDto sellerDto);

    SellerDto getSellerById(long sellerId);

}
