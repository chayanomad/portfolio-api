package com.ibm.jp.ibmconsulting.icw.api.ui.stocks;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import com.ibm.jp.ibmconsulting.icw.api.domain.StockAttributes;
import com.ibm.jp.ibmconsulting.icw.api.ui.validation.BeanValidationTarget;
import com.ibm.jp.ibmconsulting.icw.api.ui.validation.annotation.Name;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PutManageRequestBody implements BeanValidationTarget {
  @Name("stocks")
  @NotNull
  private List<Stock> stocks;

  public List<com.ibm.jp.ibmconsulting.icw.api.domain.Stock> convert() {
    return stocks.stream().map(s -> s.convert()).collect(Collectors.toList());
  }

  @Data
  @NoArgsConstructor
  protected static class Stock implements BeanValidationTarget {
    @NotNull private String id;

    @NotNull private int amount;

    public com.ibm.jp.ibmconsulting.icw.api.domain.Stock convert() {
      final StockAttributes attributes = StockAttributes.builder().amount(amount).build();
      return new com.ibm.jp.ibmconsulting.icw.api.domain.Stock(id, attributes);
    }
  }
}
