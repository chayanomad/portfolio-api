package com.ibm.jp.ibmconsulting.icw.api.domain;

import lombok.Getter;

public class StockNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  @Getter private final String id;

  public StockNotFoundException(String id) {
    super();
    this.id = id;
  }
}
