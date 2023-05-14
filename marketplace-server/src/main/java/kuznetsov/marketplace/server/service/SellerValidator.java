package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.dto.SellerDto;

public interface SellerValidator {

    void validateOrThrow(SellerProperties sellerProps, SellerDto sellerDto);

}
