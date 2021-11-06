package com.ibm.jp.ibmconsulting.icw.api.ui.validation;

public class ValidationRuntimeException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public ValidationRuntimeException(String message) {
    super(message);
  }

  public ValidationRuntimeException(String message, Exception exception) {
    super(message, exception);
  }
}
