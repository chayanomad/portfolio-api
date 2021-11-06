package com.ibm.jp.ibmconsulting.icw.api.domain;

import javax.validation.constraints.NotNull;
import com.ibm.jp.ibmconsulting.icw.api.common.validation.ValidateHelper;
import lombok.Getter;

@Getter
public class Stock {
  @NotNull private String id;

  @NotNull private StockAttributes attributes;

  public Stock(String id, StockAttributes attributes) {
    this.id = id;
    this.attributes = attributes;
    ValidateHelper.validate(this);
  }
}
