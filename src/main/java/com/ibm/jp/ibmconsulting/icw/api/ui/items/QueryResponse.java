package com.ibm.jp.ibmconsulting.icw.api.ui.items;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ItemQueryResult;
import lombok.Getter;

@Getter
public class QueryResponse {
  @NotNull private final Long matched;

  @NotNull private final List<ItemResponse> items;

  public QueryResponse(ItemQueryResult result) {
    this.matched = result.getMatched();
    this.items = 
        result
            .getItems()
            .stream()
            .map(i -> new ItemResponse(i.convert()))
            .collect(Collectors.toList());
  }
}
