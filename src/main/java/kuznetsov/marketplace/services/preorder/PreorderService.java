package kuznetsov.marketplace.services.preorder;

import kuznetsov.marketplace.services.preorder.dto.PreorderCreateDto;
import kuznetsov.marketplace.services.preorder.dto.PreorderDto;
import kuznetsov.marketplace.services.preorder.dto.PreorderDtoPage;
import kuznetsov.marketplace.services.preorder.dto.PreorderParticipantsDtoPage;

public interface PreorderService {

  PreorderDto addSellerPreorder(String sellerEmail, PreorderCreateDto preorderDto);

  PreorderDto getPreorderByIdWithCurrentUserParticipationStatus(String userEmail, long preorderId);

  PreorderDtoPage getPagedSellerPreorders(String sellerEmail, int pageNum);

  PreorderDtoPage getPagedCustomerPreorders(String customerEmail, int pageNum);

  void participateCustomerInPreorderByPreorderId(String customerEmail, long preorderId);

  PreorderParticipantsDtoPage getSellerPreorderParticipationsPageByPreorderId(
      long preorderId, String currentSellerEmail, int pageNum);

}
