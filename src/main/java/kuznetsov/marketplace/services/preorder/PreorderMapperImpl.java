package kuznetsov.marketplace.services.preorder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import kuznetsov.marketplace.database.preorder.PreorderParticipationRepository;
import kuznetsov.marketplace.database.user.CustomerRepository;
import kuznetsov.marketplace.database.user.SellerRepository;
import kuznetsov.marketplace.models.preorder.PreorderParticipation;
import kuznetsov.marketplace.models.preorder.PreorderParticipationStatus;
import kuznetsov.marketplace.models.product.Product;
import kuznetsov.marketplace.models.user.Customer;
import kuznetsov.marketplace.models.user.Seller;
import kuznetsov.marketplace.models.user.UserRole;
import kuznetsov.marketplace.services.preorder.dto.PreorderDto;
import kuznetsov.marketplace.services.preorder.dto.PreorderDtoPage;
import kuznetsov.marketplace.services.user.UserInfoService;
import kuznetsov.marketplace.services.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreorderMapperImpl implements PreorderMapper {

  private final PreorderParticipationRepository preorderParticipationRepo;
  private final SellerRepository sellerRepo;
  private final CustomerRepository customerRepo;

  private final UserInfoService userInfoService;

  @Override
  public PreorderDto toPreorderDto(Product product) {
    PreorderDto preorderDto = PreorderMapper.super.toPreorderDto(product);

    countPreorderCurrentQuantityAndSet(preorderDto, product);

    return preorderDto;
  }

  @Override
  public PreorderDto toPreorderDto(Product product, String userEmail) {
    PreorderDto preorderDto = PreorderMapper.super.toPreorderDto(product);

    countPreorderCurrentQuantityAndSet(preorderDto, product);
    findUserPreorderParticipationStatusAndSet(preorderDto, product, userEmail);

    return preorderDto;
  }

  @Override
  public List<PreorderDto> toPreorderDtoList(List<Product> products, String userEmail) {
    return products.stream()
        .map(p -> this.toPreorderDto(p, userEmail))
        .collect(Collectors.toList());
  }

  @Override
  public PreorderDtoPage toPreorderDtoPage(Page<Product> productsPage, String userEmail) {
    List<PreorderDto> preorderDtoList = toPreorderDtoList(
        productsPage.getContent(), userEmail);

    return PreorderDtoPage.builder()
        .totalPreorders(productsPage.getTotalElements())
        .totalPreordersPages(productsPage.getTotalPages())
        .preordersMaxPageSize(productsPage.getSize())
        .preordersPageNumber(productsPage.getNumber() + 1)
        .preorders(preorderDtoList)
        .build();
  }

  private void countPreorderCurrentQuantityAndSet(PreorderDto preorderDto, Product product) {
    int preorderCurrentQuantity = preorderParticipationRepo
        .countByPreorderInfo(product.getPreorderInfo());
    preorderDto.setPreorderCurrentQuantity(preorderCurrentQuantity);
  }

  private void findUserPreorderParticipationStatusAndSet(
      PreorderDto preorderDto, Product product, String userEmail) {

    preorderDto.setCurrentUserParticipationStatus(null);
    UserInfoDto userInfoDto = userInfoService.getUserInfoByEmailOrNull(userEmail);
    if (userInfoDto == null) {
      return;
    }
    UserRole userRole = userInfoDto.getRole();

    switch (userRole) {
      case SELLER:
        Seller userAsSeller = sellerRepo
            .findByUserEmail(userInfoDto.getEmail())
            .orElse(null);
        if (userAsSeller != null
            && Objects.equals(preorderDto.getSellerId(), userAsSeller.getId())) {
          preorderDto.setCurrentUserParticipationStatus(PreorderParticipationStatus.SELLER);
        }
        break;
      case CUSTOMER:
        Customer userAsCustomer = customerRepo
            .findByUserEmail(userInfoDto.getEmail())
            .orElse(null);
        PreorderParticipation userParticipation = preorderParticipationRepo
            .findByProductAndClient(product, userAsCustomer);
        if (userParticipation != null) {
          preorderDto.setCurrentUserParticipationStatus(userParticipation.getStatus());
        }
        break;
      default:
        break;
    }
  }

}
