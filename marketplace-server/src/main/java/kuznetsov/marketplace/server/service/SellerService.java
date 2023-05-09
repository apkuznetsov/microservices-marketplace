package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.dto.SellerDto;

public interface SellerService {

    SellerDto updateSellerById(long sellerId, SellerDto sellerDto);

    SellerDto getSellerById(long sellerId);

}
