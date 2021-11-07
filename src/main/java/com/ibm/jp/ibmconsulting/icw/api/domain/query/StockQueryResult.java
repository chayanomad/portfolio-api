package com.ibm.jp.ibmconsulting.icw.api.domain.query;

import java.util.List;
import com.ibm.jp.ibmconsulting.icw.api.infrastructure.Stocks;
import lombok.Getter;

@Getter
public class StockQueryResult {
  private final long matched;
  private final List<Stocks> stocks;

  public StockQueryResult(long matched, List<Stocks> stocks) {
    this.matched = matched;
    this.stocks = stocks;
  }
}
