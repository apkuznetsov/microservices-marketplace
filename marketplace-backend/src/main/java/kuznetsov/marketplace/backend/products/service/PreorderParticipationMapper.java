package kuznetsov.marketplace.backend.products.service;

import kuznetsov.marketplace.backend.products.domain.PreorderInfo;
import kuznetsov.marketplace.backend.products.domain.PreorderParticipation;
import kuznetsov.marketplace.backend.products.domain.PreorderParticipationStatus;
import kuznetsov.marketplace.backend.products.dto.PreorderParticipantDto;
import kuznetsov.marketplace.backend.products.dto.PreorderParticipantsDtoPage;
import kuznetsov.marketplace.backend.roles.customers.domain.Customer;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public interface PreorderParticipationMapper {

    default PreorderParticipation toPreorderParticipation(
            PreorderInfo preorderInfo, Customer customer) {

        return PreorderParticipation.builder()
                .preorderInfo(preorderInfo)
                .customer(customer)
                .status(PreorderParticipationStatus.CLIENT_AWAITING_PAYMENT)
                .build();
    }

    default PreorderParticipantDto toPreorderParticipantDto(
            PreorderParticipation customerParticipation) {

        Customer customer = customerParticipation.getCustomer();
        String customerEmail = customer.getUser().getEmail();

        return PreorderParticipantDto.builder()
                .email(customerEmail)
                .name(customer.getName())
                .address(customer.getAddress())
                .preorderedQuantity(1)
                .participationStatus(customerParticipation.getStatus())
                .build();
    }

    default PreorderParticipantsDtoPage toPreorderParticipationsDtoPage(
            Long preorderId, Page<PreorderParticipation> pagedPreorderParticipations) {

        List<PreorderParticipantDto> preorderParticipants = pagedPreorderParticipations.stream()
                .map(this::toPreorderParticipantDto)
                .collect(Collectors.toList());

        return PreorderParticipantsDtoPage.builder()
                .totalPreorderParticipants(pagedPreorderParticipations.getTotalElements())
                .totalPreorderParticipantsPages(pagedPreorderParticipations.getTotalPages())
                .preorderParticipantsMaxPageSize(pagedPreorderParticipations.getSize())
                .preorderParticipantsPageNumber(pagedPreorderParticipations.getNumber() + 1)
                .preorderId(preorderId)
                .preorderParticipants(preorderParticipants)
                .build();
    }

}
