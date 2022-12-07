package kuznetsov.marketplace.web.preorder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.net.URI;
import java.security.Principal;
import javax.validation.Valid;
import kuznetsov.marketplace.models.user.UserRole;
import kuznetsov.marketplace.services.exception.ServiceException;
import kuznetsov.marketplace.services.preorder.PreorderService;
import kuznetsov.marketplace.services.preorder.dto.PreorderDto;
import kuznetsov.marketplace.services.preorder.dto.PreorderDtoPage;
import kuznetsov.marketplace.services.preorder.dto.PreorderParticipantsDtoPage;
import kuznetsov.marketplace.services.user.UserService;
import kuznetsov.marketplace.services.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = PreorderController.PREORDERS_URL)
@RequiredArgsConstructor
@Slf4j
public class PreorderController {

  public static final String PREORDERS_URL = "/api/v1/preorders";
  public static final String PREORDERS_PARTICIPATIONS_URL = "/participations";

  private final PreorderService preorderService;
  private final UserService userService;

  @Operation(summary = "Создать предзаказ продавца.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Получен предзаказ.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = PreorderDto.class))}),
      @ApiResponse(responseCode = "404", description = "Категория не найдена.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ServiceException.class))}),
      @ApiResponse(responseCode = "404", description = "Продавец не найден.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ServiceException.class))}),
      @ApiResponse(responseCode = "403",
          description = "Текущий пользователь не имеет права создавать предзаказ.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ServiceException.class))})})
  @PostMapping
  public ResponseEntity<PreorderDto> addCurrentSellerPreorder(
      @RequestBody @Valid PreorderDto preorder, Principal principal) {
    if (principal == null) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    String sellerEmail = principal.getName();
    log.info("The seller with {} email is trying to add the preorder {}.",
        sellerEmail, preorder.getTitle());

    PreorderDto createdPreorder = preorderService.addSellerPreorder(sellerEmail, preorder);

    URI createdPreorderUri = URI.create(PREORDERS_URL + "/" + createdPreorder.getId());
    return ResponseEntity.created(createdPreorderUri).body(createdPreorder);
  }

  @Operation(summary = "Получить описание предзаказа.\n"
      + "1.1. Если авторизован продавец и это его предзаказ,"
      + " то в currentUserParticipationStatus будет статус SELLER.\n"
      + "1.2. Если авторизован продавец и это НЕ ЕГО предзаказ,"
      + " то в currentUserParticipationStatus будет null"
      + " (продавец не связан с этим предзаказом, только просматривает).\n"
      + "2.1. Если авторизован покупатель и это предзаказ, в котором он участвует,"
      + " то в currentUserParticipationStatus будут его статусы как покупателя"
      + " (например, CLIENT_AWAITING_PAYMENT).\n"
      + "2.2. Если авторизован покупатель и это предзаказ, в котором он НЕ УЧАСТВУЕТ,"
      + " то в currentUserParticipationStatus будет null"
      + " (покупатель не связан с этим предзаказом, только просматривает).\n"
      + "3.1. Если пользователь неавторизован, то currentUserParticipationStatus будет null"
      + " (пользователь не связан с этим предзаказом, только просматривает).")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Получен предзаказ с указанным номером.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = PreorderDto.class))}),
      @ApiResponse(responseCode = "404", description = "Предзаказ не найден.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ServiceException.class))})})
  @GetMapping("/{preorderId}")
  public ResponseEntity<PreorderDto> getPreorderByIdWithCurrentUserParticipationStatus(
      @PathVariable Long preorderId, Principal principal) {
    if (preorderId == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    String userEmail = principal != null
        ? principal.getName()
        : null;
    log.info("The user with {} email is trying to get the preorder with {} id.",
        userEmail, preorderId);

    PreorderDto preorder = preorderService
        .getPreorderByIdWithCurrentUserParticipationStatus(userEmail, preorderId);

    return ResponseEntity.ok().body(preorder);
  }

  @Operation(summary = "Получить страницу всех предзаказов текущего пользователя " +
      "– продавца или покупателя. " +
      "Продавец получает страницу предзаказов, которые он создал. " +
      "Покупатель получает страницу предзаказов, в которых он участвует.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Получена страница предзаказов текущего пользователя.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = PreorderDtoPage.class))}),
      @ApiResponse(responseCode = "400", description = "Неположительный номер страницы.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ServiceException.class))})})
  @GetMapping
  public ResponseEntity<PreorderDtoPage> getAllCurrentUserPreordersPage(
      @RequestParam(name = "page", required = false, defaultValue = "1") int pageNum,
      Principal principal) {

    String userEmail = principal != null
        ? principal.getName()
        : null;
    UserDto user = userService.getUserByEmail(userEmail);
    UserRole currentUserRole = user != null
        ? user.getRole()
        : null;
    log.info(
        "The user with {} email is trying to get his/her paged preorders with page number {}.",
        userEmail, pageNum);

    PreorderDtoPage pagedPreorders;
    if (currentUserRole == UserRole.SELLER) {
      pagedPreorders = preorderService.getPagedSellerPreorders(userEmail, pageNum);
    } else if (currentUserRole == UserRole.CUSTOMER) {
      pagedPreorders = preorderService.getPagedCustomerPreorders(userEmail, pageNum);
    } else {
      pagedPreorders = null;
    }

    return ResponseEntity.ok().body(pagedPreorders);
  }

  @Operation(summary = "Подписать текущего покупатель на предзаказ по номеру предзаказа.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201",
          description = "Теперь покупатель принимает участие в предзаказе.",
          content = {@Content(mediaType = "application/json")}),
      @ApiResponse(responseCode = "404", description = "Покупатель не найден.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ServiceException.class))}),
      @ApiResponse(responseCode = "404", description = "Товар не найден.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ServiceException.class))}),
      @ApiResponse(responseCode = "400", description = "Этот товар невозможно предзаказать.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ServiceException.class))}),
      @ApiResponse(responseCode = "403",
          description = "Текущий пользователь не имеет права участвовать в предзаказе.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ServiceException.class))})})
  @PostMapping("/{preorderId}" + PREORDERS_PARTICIPATIONS_URL)
  public ResponseEntity participateCurrentCustomerInPreorderByPreorderId(
      @PathVariable Long preorderId, Principal principal) {
    if (preorderId == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    if (principal == null) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    String customerEmail = principal.getName();
    log.info("The customer with {} email is trying to participate in the preorder with {} id.",
        customerEmail, preorderId);

    preorderService.participateCustomerInPreorderByPreorderId(customerEmail, preorderId);

    URI createdParticipationUri = URI.create(
        PREORDERS_URL + "/" + preorderId + PREORDERS_PARTICIPATIONS_URL);
    return ResponseEntity.created(createdParticipationUri).build();
  }

  @Operation(summary = "Получить страницу участников предзаказа текущего продавца.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Получена страница участников указанного предзаказа текущего продавца.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = PreorderParticipantsDtoPage.class))}),
      @ApiResponse(responseCode = "400", description = "Неположительный номер страницы.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ServiceException.class))}),
      @ApiResponse(responseCode = "403",
          description = "Текущий пользователь не имеет права получать"
              + " страницу участников предзаказа.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ServiceException.class))})})
  @GetMapping("/{preorderId}" + PREORDERS_PARTICIPATIONS_URL)
  public ResponseEntity<PreorderParticipantsDtoPage>
  getCurrentSellerPreorderParticipationsPageByPreorderId(
      @RequestParam(name = "page", required = false, defaultValue = "1") int pageNum,
      @PathVariable Long preorderId,
      Principal principal) {

    if (preorderId == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    if (principal == null) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    String sellerEmail = principal.getName();
    log.info("The seller with {} email is trying to get participations page "
            + "of his/her the preorder with {} id.",
        sellerEmail, pageNum);

    PreorderParticipantsDtoPage pagedPreorderParticipants = preorderService
        .getSellerPreorderParticipationsPageByPreorderId(sellerEmail, preorderId, pageNum);

    return ResponseEntity.ok().body(pagedPreorderParticipants);
  }

}
