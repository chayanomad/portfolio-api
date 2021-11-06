package com.ibm.jp.ibmconsulting.icw.api.ui.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class BadRequestParamsExceptionHandler extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler(BadRequestParamsException.class)
  public ResponseEntity<ErrorResponse> handleException(BadRequestParamsException e) {
    log.error("E400-001", e);
    return ErrorResponse.build("E400-001", e.getErrors());
  }
}
