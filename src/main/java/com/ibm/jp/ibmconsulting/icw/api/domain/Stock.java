package com.ibm.jp.ibmconsulting.icw.api.domain;

import javax.validation.constraints.NotNull;
import com.ibm.jp.ibmconsulting.icw.api.common.validation.ValidateHelper;
import lombok.Getter;

@Getter
public class Stock {
  @NotNull private String itemId;

  @NotNull private StockAttributes attributes;

  public Stock(String itemId, StockAttributes attributes) {
    this.itemId = itemId;
    this.attributes = attributes;
    ValidateHelper.validate(this);
  }
}
