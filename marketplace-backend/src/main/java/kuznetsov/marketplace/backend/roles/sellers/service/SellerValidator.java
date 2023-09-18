package kuznetsov.marketplace.backend.roles.sellers.service;

import kuznetsov.marketplace.backend.roles.sellers.dto.SellerDto;

public interface SellerValidator {

    void validateOrThrow(SellerProperties sellerProps, SellerDto sellerDto);

}
