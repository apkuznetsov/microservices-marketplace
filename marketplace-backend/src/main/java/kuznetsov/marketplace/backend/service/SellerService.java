package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.dto.SellerDto;

public interface SellerService {

    SellerDto updateSellerById(long sellerId, SellerDto sellerDto);

    SellerDto getSellerById(long sellerId);

}
