package kuznetsov.marketplace.core.roles.sellers.service;

import kuznetsov.marketplace.core.roles.sellers.dto.SellerDto;

public interface SellerService {

    SellerDto updateSellerById(long sellerId, SellerDto sellerDto);

    SellerDto getSellerById(long sellerId);

}
