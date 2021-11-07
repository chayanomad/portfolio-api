package com.ibm.jp.ibmconsulting.icw.api.ui.error;

import com.ibm.jp.ibmconsulting.icw.api.domain.StockNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class StockNotFoundExceptionHandler extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler(StockNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(StockNotFoundException e) {
    log.error("E400-002", e);
    return ErrorResponse.build("E400-002", e.getId());
  }
}
