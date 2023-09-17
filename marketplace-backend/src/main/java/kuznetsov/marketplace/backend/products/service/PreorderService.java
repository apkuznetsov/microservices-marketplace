package kuznetsov.marketplace.backend.products.service;

import kuznetsov.marketplace.backend.products.dto.PreorderDto;
import kuznetsov.marketplace.backend.products.dto.PreorderDtoPage;
import kuznetsov.marketplace.backend.products.dto.PreorderParticipantsDtoPage;

public interface PreorderService {

    PreorderDto addSellerPreorder(String sellerEmail, PreorderDto preorderDto);

    PreorderDto getPreorderByIdWithCurrentUserParticipationStatus(String userEmail, long preorderId);

    PreorderDtoPage getPagedSellerPreorders(String sellerEmail, int pageNum);

    PreorderDtoPage getPagedCustomerPreorders(String customerEmail, int pageNum);

    void participateCustomerInPreorderByPreorderId(String customerEmail, long preorderId);

    PreorderParticipantsDtoPage getSellerPreorderParticipationsPageByPreorderId(
            String currentSellerEmail, long preorderId, int pageNum);

}
