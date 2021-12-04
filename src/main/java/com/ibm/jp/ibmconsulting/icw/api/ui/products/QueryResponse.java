package com.ibm.jp.ibmconsulting.icw.api.ui.products;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ProductQueryResult;
import lombok.Getter;

@Getter
public class QueryResponse {
  @NotNull private final Long matched;

  @NotNull private final List<ProductResponse> products;

  public QueryResponse(ProductQueryResult result) {
    this.matched = result.getMatched();
    this.products = 
        result
            .getProducts()
            .stream()
            .map(i -> new ProductResponse(i.convert()))
            .collect(Collectors.toList());
  }
}
