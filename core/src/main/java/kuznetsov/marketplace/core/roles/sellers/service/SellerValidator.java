package kuznetsov.marketplace.core.roles.sellers.service;

import kuznetsov.marketplace.core.roles.sellers.dto.SellerDto;

public interface SellerValidator {

    void validateOrThrow(SellerProperties sellerProps, SellerDto sellerDto);

}
