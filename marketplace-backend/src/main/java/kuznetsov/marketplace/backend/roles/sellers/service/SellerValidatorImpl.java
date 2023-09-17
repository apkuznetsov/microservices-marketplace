package kuznetsov.marketplace.backend.roles.sellers.service;

import kuznetsov.marketplace.backend.roles.sellers.dto.SellerDto;
import org.springframework.stereotype.Component;

@Component
public class SellerValidatorImpl implements SellerValidator {

    @Override
    public void validateOrThrow(SellerProperties sellerProps, SellerDto sellerDto) {

    }

}
