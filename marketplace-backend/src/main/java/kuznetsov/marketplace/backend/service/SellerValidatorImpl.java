package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.dto.SellerDto;
import org.springframework.stereotype.Component;

@Component
public class SellerValidatorImpl implements SellerValidator {

    @Override
    public void validateOrThrow(SellerProperties sellerProps, SellerDto sellerDto) {

    }

}
