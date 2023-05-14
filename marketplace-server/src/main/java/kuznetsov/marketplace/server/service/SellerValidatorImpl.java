package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.dto.SellerDto;
import org.springframework.stereotype.Component;

@Component
public class SellerValidatorImpl implements SellerValidator {

    @Override
    public void validateOrThrow(SellerProperties sellerProps, SellerDto sellerDto) {

    }

}
