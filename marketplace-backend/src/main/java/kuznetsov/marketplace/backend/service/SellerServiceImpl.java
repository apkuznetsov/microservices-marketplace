package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.domain.Seller;
import kuznetsov.marketplace.backend.dto.SellerDto;
import kuznetsov.marketplace.backend.exception.ServiceException;
import kuznetsov.marketplace.backend.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerProperties sellerProperties;
    private final SellerMapper sellerMapper;
    private final SellerValidator sellerValidator;

    private final SellerRepository sellerRepo;

    @Override
    public SellerDto updateSellerById(long sellerId, SellerDto sellerDto) {
        sellerValidator.validateOrThrow(sellerProperties, sellerDto);

        if (!sellerRepo.existsById(sellerId)) {
            throw new ServiceException(SellerErrorCode.SELLER_NOT_FOUND);
        }

        Seller newSeller = sellerMapper.toSeller(sellerDto);
        newSeller.setId(sellerId);
        Seller updatedSeller = sellerRepo.saveAndFlush(newSeller);

        return sellerMapper.toSellerDto(updatedSeller);
    }

    @Override
    public SellerDto getSellerById(long sellerId) {
        Seller seller = sellerRepo.findById(sellerId)
                .orElseThrow(() -> new ServiceException(SellerErrorCode.SELLER_NOT_FOUND));

        return sellerMapper.toSellerDto(seller);
    }

}
