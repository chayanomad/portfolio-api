package com.ibm.jp.ibmconsulting.icw.api.ui.stocks;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Min;

import com.ibm.jp.ibmconsulting.icw.api.common.validation.AlphaNumericList;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.PaginationCondition;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.StockQueryCondition;
import com.ibm.jp.ibmconsulting.icw.api.ui.validation.BeanValidationTarget;
import com.ibm.jp.ibmconsulting.icw.api.ui.validation.annotation.Name;
import lombok.Data;

@Data
public class QueryRequestParams implements BeanValidationTarget {
  @Name("id")
  @AlphaNumericList
  private List<String> ids;

  @Name("offset")
  @Min(0)
  private int offset;

  @Name("limit")
  @Min(0)
  private int limit;

  public QueryRequestParams(List<String> ids, Integer offset, Integer limit) {
    this.ids = ids;
    this.offset =
        Objects.nonNull(offset)
            ? offset.intValue()
            : 0;
    this.limit = 
        Objects.nonNull(limit)
            ? limit.intValue()
            : 0;
  }

  public StockQueryCondition toStockQueryCondition() {
    return StockQueryCondition.builder().ids(ids).build();
  }

  public PaginationCondition toPaginationCondition() {
    return new PaginationCondition(offset, limit);
  }
}
