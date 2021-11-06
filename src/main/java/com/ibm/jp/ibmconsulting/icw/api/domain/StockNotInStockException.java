package com.ibm.jp.ibmconsulting.icw.api.domain;

import lombok.Getter;

public class StockNotInStockException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  @Getter private final String id;
  @Getter private final int amount;

  public StockNotInStockException(String id, int amount) {
    super();
    this.id = id;
    this.amount = amount;
  }
}
