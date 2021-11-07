package com.ibm.jp.ibmconsulting.icw.api.ui.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ErrorResource implements ErrorController {
  
  @RequestMapping("/error")
  public ResponseEntity<ErrorResponse> handleError() {
    log.error("想定しないエラーが発生しました。");
    return ErrorResponse.build("E500-001");
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
