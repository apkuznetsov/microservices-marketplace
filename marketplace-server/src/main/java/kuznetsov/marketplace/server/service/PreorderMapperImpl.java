package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.domain.Customer;
import kuznetsov.marketplace.server.domain.PreorderParticipation;
import kuznetsov.marketplace.server.domain.PreorderParticipationStatus;
import kuznetsov.marketplace.server.domain.Product;
import kuznetsov.marketplace.server.domain.Seller;
import kuznetsov.marketplace.server.domain.UserRole;
import kuznetsov.marketplace.server.dto.PreorderDto;
import kuznetsov.marketplace.server.dto.PreorderDtoPage;
import kuznetsov.marketplace.server.dto.UserDto;
import kuznetsov.marketplace.server.repository.CustomerRepository;
import kuznetsov.marketplace.server.repository.PreorderParticipationRepository;
import kuznetsov.marketplace.server.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PreorderMapperImpl implements PreorderMapper {

    private final PreorderParticipationRepository preorderParticipationRepo;
    private final SellerRepository sellerRepo;
    private final CustomerRepository customerRepo;

    private final UserService userService;

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
        UserDto userDto = userService.getUserByEmail(userEmail);
        if (userDto == null) {
            return;
        }
        UserRole userRole = userDto.getRole();

        switch (userRole) {
            case SELLER:
                Seller userAsSeller = sellerRepo
                        .findByUserEmail(userDto.getEmail())
                        .orElse(null);
                if (userAsSeller != null
                        && Objects.equals(preorderDto.getSellerId(), userAsSeller.getId())) {
                    preorderDto.setCurrentUserParticipationStatus(PreorderParticipationStatus.SELLER);
                }
                break;
            case CUSTOMER:
                Customer userAsCustomer = customerRepo
                        .findByUserEmail(userDto.getEmail())
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
