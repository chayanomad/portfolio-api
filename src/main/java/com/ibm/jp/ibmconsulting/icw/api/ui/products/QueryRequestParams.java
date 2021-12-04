package com.ibm.jp.ibmconsulting.icw.api.ui.products;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.Min;
import com.ibm.jp.ibmconsulting.icw.api.common.validation.SelectiveItem;
import com.ibm.jp.ibmconsulting.icw.api.common.validation.SelectiveItemList;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ProductQueryCondition;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.PaginationCondition;
import com.ibm.jp.ibmconsulting.icw.api.ui.error.Error;
import com.ibm.jp.ibmconsulting.icw.api.ui.validation.BeanValidationTarget;
import com.ibm.jp.ibmconsulting.icw.api.ui.validation.CustomValidationTarget;
import com.ibm.jp.ibmconsulting.icw.api.ui.validation.annotation.Name;
import lombok.Data;

@Data
public class QueryRequestParams implements BeanValidationTarget, CustomValidationTarget {
  @Name("name")
  private String name;

  @Name("category")
  @SelectiveItemList(items = {"FRUIT", "VEGETABLE", "JUICE"})
  private List<String> categories;

  @Name("minprice")
  @Min(0)
  private Integer minPrice;

  @Name("maxprice")
  @Min(0)
  private Integer maxPrice;

  @Name("sort")
  @SelectiveItem(items = {"id", "name", "price", "stock"})
  private String sort;

  @Name("order")
  @SelectiveItem(items = {"asc", "desc"})
  private String order;

  @Name("offset")
  @Min(0)
  private int offset;

  @Name("limit")
  @Min(0)
  private int limit;

  public QueryRequestParams(String name, List<String> categories, Integer minPrice, Integer maxPrice, String sort, String order, Integer offset, Integer limit) {
    this.name = name;
    this.categories = categories;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
    this.sort = sort;
    this.order = order;
    this.offset =
        Objects.nonNull(offset)
            ? offset.intValue()
            : 0;
    this.limit = 
        Objects.nonNull(limit)
            ? limit.intValue()
            : 0;
  }

  public ProductQueryCondition toItemQueryCondition() {
    return ProductQueryCondition.builder()
        .name(name)
        .categories(categories)
        .minPrice(minPrice)
        .maxPrice(maxPrice)
        .build();
  }

  public PaginationCondition toPaginationCondition() {
    return new PaginationCondition(sort, "id", order, "asc", offset, limit);
  }

  @Override
  public Set<Error> customValidate() {
    final Set<Error> errors = new HashSet<>();

    if (Objects.nonNull(minPrice) && Objects.nonNull(maxPrice)) {
      if (minPrice > maxPrice) {
        final String code = "ILLEGAL";
        final String message =
            "minpriceにmaxpriceよりも大きな値を指定することはできません。";
        errors.add(new Error("minprice", code, message));
      }
    }

    return errors;
  }
}
