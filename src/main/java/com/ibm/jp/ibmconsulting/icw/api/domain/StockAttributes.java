package com.ibm.jp.ibmconsulting.icw.api.domain;

import javax.validation.constraints.NotNull;

import com.ibm.jp.ibmconsulting.icw.api.common.validation.ValidateHelper;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder(builderClassName = "Builder", buildMethodName = "build")
@Slf4j
public class StockAttributes {
  @NotNull private int amount;

  public boolean notInStock(int quantity) {
    log.info("quantity: {}", quantity);
    log.info("amount: {}", amount);
    return amount < quantity;
  }

  public StockAttributes buyStockUpdated(int quantity) {
    return StockAttributes.builder().amount(amount-quantity).build();
  }

  public static class Builder {
    public StockAttributes build() {
      final StockAttributes attributes =
        new StockAttributes(amount);
      ValidateHelper.validate(attributes);
      return attributes;
    }
  }
}
