package com.ibm.jp.ibmconsulting.icw.api.ui.healthcheck;

import lombok.Getter;

@Getter
public class HealthCheckResponse {
  private final String message;

  public HealthCheckResponse(String message) {
    this.message = message;
  }
}
