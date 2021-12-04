package com.ibm.jp.ibmconsulting.icw.api.domain.query;

import java.util.List;
import lombok.Getter;

@Getter
public class ProductQueryResult {
  private final long matched;
  private final List<ProductSummary> products;

  public ProductQueryResult(long matched, List<ProductSummary> products) {
    this.matched = matched;
    this.products = products;
  }
}
