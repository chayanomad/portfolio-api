package com.ibm.jp.ibmconsulting.icw.api.ui.error;

import java.util.HashSet;
import java.util.Set;
import javax.json.bind.annotation.JsonbProperty;
import com.ibm.jp.ibmconsulting.icw.api.common.config.Properties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@AllArgsConstructor
@ToString
public final class ErrorResponse {

  @Setter
  @Getter
  @JsonbProperty("code")
  private String code;

  @Setter
  @Getter
  @JsonbProperty("message")
  private String message;

  @Setter
  @Getter
  @JsonbProperty("errors")
  private Set<Error> errors;

  private ErrorResponse() {};

  @SuppressWarnings("PMD.ShortMethodName")
  public static ErrorResponse of(String code, Object... messageParams) {
    return of(code, new HashSet<Error>(), messageParams);
  }

  @SuppressWarnings("PMD.ShortMethodName")
  public static ErrorResponse of(String code, Set<Error> errors, Object... messageParams) {
    final String message = Properties.API_ERRORCODE.getValue(code, 1);
    final String buildedMessage = String.format(message, messageParams);

    final ErrorResponse response = new ErrorResponse();
    response.setCode(code);
    response.setMessage(buildedMessage);
    response.setErrors(errors);
    return response;
  }

  public static ResponseEntity<ErrorResponse> build(String code, Object... messageParams) {
    return build(code, new HashSet<Error>(), messageParams);
  }

  public static ResponseEntity<ErrorResponse> build(String code, Set<Error> errors, Object... messageParams) {
    final int statusCode = Properties.API_ERRORCODE.getValueInt(code, 0);
    return new ResponseEntity<ErrorResponse>(of(code, errors, messageParams), HttpStatus.valueOf(statusCode));
  }
}

