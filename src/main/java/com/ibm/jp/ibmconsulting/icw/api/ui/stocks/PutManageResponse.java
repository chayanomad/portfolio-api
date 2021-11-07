package com.ibm.jp.ibmconsulting.icw.api.ui.stocks;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import com.ibm.jp.ibmconsulting.icw.api.domain.Stock;
import lombok.Getter;

@Getter
public class PutManageResponse {
  @NotNull private final List<StockResponse> stocks;

  public PutManageResponse(List<Stock> result) {
    this.stocks =
        result
            .stream()
            .map(s -> new StockResponse(s))
            .collect(Collectors.toList());
  }
}
