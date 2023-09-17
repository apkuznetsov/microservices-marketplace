package kuznetsov.marketplace.backend.products.service;

import kuznetsov.marketplace.backend.products.domain.PreorderParticipation;
import kuznetsov.marketplace.backend.products.domain.PreorderParticipationStatus;
import kuznetsov.marketplace.backend.products.domain.Product;
import kuznetsov.marketplace.backend.products.dto.PreorderDto;
import kuznetsov.marketplace.backend.products.dto.PreorderDtoPage;
import kuznetsov.marketplace.backend.products.repos.PreorderParticipationRepo;
import kuznetsov.marketplace.backend.roles.customers.domain.Customer;
import kuznetsov.marketplace.backend.roles.customers.repos.CustomerRepo;
import kuznetsov.marketplace.backend.roles.sellers.domain.Seller;
import kuznetsov.marketplace.backend.roles.sellers.repos.SellerRepo;
import kuznetsov.marketplace.backend.users.domain.Role;
import kuznetsov.marketplace.backend.users.dto.UserDto;
import kuznetsov.marketplace.backend.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PreorderMapperImpl implements PreorderMapper {

    private final PreorderParticipationRepo preorderParticipationRepo;
    private final SellerRepo sellerRepo;
    private final CustomerRepo customerRepo;

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
        UserDto userDto = userService.getUserByEmailOrNull(userEmail);
        if (userDto == null) {
            return;
        }
        Role role = userDto.getRole();

        switch (role) {
            case SELLER:
                Seller userAsSeller = sellerRepo
                        .findByEmail(userDto.getEmail())
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
