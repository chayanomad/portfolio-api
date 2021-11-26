package com.ibm.jp.ibmconsulting.icw.api.domain.query;

import java.util.List;
import lombok.Getter;

@Getter
public class ItemQueryResult {
  private final long matched;
  private final List<ItemSummary> items;

  public ItemQueryResult(long matched, List<ItemSummary> items) {
    this.matched = matched;
    this.items = items;
  }
}
