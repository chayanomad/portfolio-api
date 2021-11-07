package com.ibm.jp.ibmconsulting.icw.api.ui.stocks;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.StockQueryResult;
import lombok.Getter;

@Getter
public class QueryResponse {
  @NotNull private final Long matched;

  @NotNull private final List<StockResponse> stocks;
  
  public QueryResponse(StockQueryResult result) {
    this.matched = result.getMatched();
    this.stocks = 
        result
            .getStocks()
            .stream()
            .map(s -> new StockResponse(s.convert()))
            .collect(Collectors.toList());
  }
}
