package com.ibm.jp.ibmconsulting.icw.api.ui.error;

import java.util.Set;

public class BadRequestParamsException extends RuntimeException {
  private static final long serialVersionUID = -2870980956809763545L;
  private final Set<Error> errors;

  public BadRequestParamsException(Set<Error> errors) {
    super();
    this.errors = errors;
  }

  public Set<Error> getErrors() {
    return errors;
  }
}
