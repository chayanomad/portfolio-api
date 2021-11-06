package com.ibm.jp.ibmconsulting.icw.api.ui.healthcheck;

import com.ibm.jp.ibmconsulting.icw.api.common.log.Logging;
import com.ibm.jp.ibmconsulting.icw.api.domain.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("healthcheck")
public class HealthCheckResource {
  @Autowired private SampleRepository repository;
  
  /** アプリケーション稼働確認用API */
  @GetMapping(
      value = "app/status",
      produces = { "application/json" })
  @Logging
  public ResponseEntity<HealthCheckResponse> getApplicationStatus() {
    return new ResponseEntity<HealthCheckResponse>(new HealthCheckResponse("OK"), HttpStatus.OK);
  }

  /** DB接続を含むアプリケーション稼働確認用API */
  @GetMapping(
      value = "db/status",
      produces = { "application/json" })
  @Logging
  public ResponseEntity<HealthCheckResponse> getDbConnectionStatus() {
    repository.touchToRepository();
    return new ResponseEntity<HealthCheckResponse>(new HealthCheckResponse("OK"), HttpStatus.OK);
  }
}
