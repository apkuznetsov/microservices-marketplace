package kuznetsov.marketplace.services.exception;

import java.io.Serializable;
import org.springframework.http.HttpStatus;

public interface ServiceErrorCode extends Serializable {

  String getName();

  String getMessage();

  default HttpStatus getStatus() {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }

}
