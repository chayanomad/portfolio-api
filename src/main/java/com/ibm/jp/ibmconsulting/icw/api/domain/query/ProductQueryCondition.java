package com.ibm.jp.ibmconsulting.icw.api.domain.query;

import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductQueryCondition {
  private String name;
  private List<String> categories;
  private Integer minPrice;
  private Integer maxPrice;
  
  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }

  public Optional<List<String>> getCategories() {
    return Optional.ofNullable(categories);
  }

  public Optional<Integer> getMinPrice() {
    return Optional.ofNullable(minPrice);
  }

  public Optional<Integer> getMaxPrice() {
    return Optional.ofNullable(maxPrice);
  }
}
