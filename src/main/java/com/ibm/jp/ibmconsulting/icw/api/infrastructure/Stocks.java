package com.ibm.jp.ibmconsulting.icw.api.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.ibm.jp.ibmconsulting.icw.api.common.validation.ValidateHelper;
import com.ibm.jp.ibmconsulting.icw.api.domain.Stock;
import com.ibm.jp.ibmconsulting.icw.api.domain.StockAttributes;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
@Table(name = "STOCKS")
public class Stocks {
  @Id private String id;

  @Column(name = "AMOUNT")
  @NotNull
  private int amount;

  public Stocks(String id, int amount) {
    this.id = id;
    this.amount = amount;
    ValidateHelper.validate(this);
  }

  public Stock convert() {
    final StockAttributes attributes = 
        StockAttributes.builder().amount(amount).build();
    return new Stock(id, attributes);
  }

  public void update(StockAttributes attributes) {
    this.amount = attributes.getAmount();
  }
}
