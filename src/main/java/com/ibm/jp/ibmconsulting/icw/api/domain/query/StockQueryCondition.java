package com.ibm.jp.ibmconsulting.icw.api.domain.query;

import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class StockQueryCondition {
  private List<String> ids;

  public Optional<List<String>> getIds() {
    return Optional.ofNullable(ids);
  }
}
