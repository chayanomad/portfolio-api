package com.ibm.jp.ibmconsulting.icw.api.ui.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ErrorResource implements ErrorController {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({ NotFoundException.class })
  public ResponseEntity<ErrorResponse> handle404() {
    log.error("404 Not Found");
    return ErrorResponse.build("E404-001");
  }
  
  @RequestMapping("/error")
  public ResponseEntity<ErrorResponse> handleError(HttpServletRequest request) {
    Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    if (statusCode != null && statusCode.toString().equals("404")) {
      log.error("404 Not Found");
      return ErrorResponse.build("E404-001");
    }
    log.error("想定しないエラーが発生しました。");
    return ErrorResponse.build("E500-001");
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
