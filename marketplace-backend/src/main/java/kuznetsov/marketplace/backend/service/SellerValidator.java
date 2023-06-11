package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.dto.SellerDto;

public interface SellerValidator {

    void validateOrThrow(SellerProperties sellerProps, SellerDto sellerDto);

}
