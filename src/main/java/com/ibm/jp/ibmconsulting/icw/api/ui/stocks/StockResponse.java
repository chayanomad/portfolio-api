package com.ibm.jp.ibmconsulting.icw.api.ui.stocks;

import javax.validation.constraints.NotNull;
import com.ibm.jp.ibmconsulting.icw.api.common.validation.ValidateHelper;
import com.ibm.jp.ibmconsulting.icw.api.domain.Stock;
import lombok.Getter;

@Getter
public class StockResponse {
  @NotNull private final String id;

  @NotNull private final int amount;

  public StockResponse(Stock stock) {
    this.id = stock.getId();
    this.amount = stock.getAttributes().getAmount();
    ValidateHelper.validate(this);
  }
}
