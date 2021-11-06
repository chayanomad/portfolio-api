package com.ibm.jp.ibmconsulting.icw.api.ui.error;

import javax.json.bind.annotation.JsonbProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Error {
  @JsonbProperty("field")
  private final String field;

  @JsonbProperty("code")
  private final String code;

  @JsonbProperty("message")
  private final String message;

  public Error(String field, String code, String message) {
    this.field = field;
    this.code = code;
    this.message = message;
  }
}
