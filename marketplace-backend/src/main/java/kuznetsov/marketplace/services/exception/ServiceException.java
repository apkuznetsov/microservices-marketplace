package kuznetsov.marketplace.services.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonPropertyOrder({"timestamp", "status", "error", "message"})
@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
@Getter
public class ServiceException extends RuntimeException {

  @JsonProperty("timestamp")
  private final String timestamp;

  @JsonProperty("status")
  private final HttpStatus status;

  @JsonProperty("error")
  private final String error;

  @JsonProperty("message")
  private final String message;

  public ServiceException(ServiceErrorCode serviceErrorCode) {
    this.timestamp = LocalDateTime.now().toString();
    this.status = serviceErrorCode.getStatus();
    this.error = serviceErrorCode.getName();
    this.message = serviceErrorCode.getMessage();
  }

}
