package com.ibm.jp.ibmconsulting.icw.api.domain;

import javax.validation.constraints.NotNull;
import com.ibm.jp.ibmconsulting.icw.api.common.validation.ValidateHelper;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderClassName = "Builder", buildMethodName = "build")
public class StockAttributes {
  @NotNull private int amount;

  public boolean notInStock(int quantity) {
    return amount < quantity;
  }

  public StockAttributes getStockUpdated(int quantity) {
    return StockAttributes.builder().amount(quantity).build();
  }

  public StockAttributes buyStockUpdated(int quantity) {
    return getStockUpdated(amount-quantity);
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
