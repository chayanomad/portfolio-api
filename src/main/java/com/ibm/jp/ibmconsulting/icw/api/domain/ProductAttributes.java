package com.ibm.jp.ibmconsulting.icw.api.domain;

import javax.validation.constraints.NotNull;
import com.ibm.jp.ibmconsulting.icw.api.common.validation.ValidateHelper;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderClassName = "Builder", buildMethodName = "build")
public class ProductAttributes {
  @NotNull private Category category;

  @NotNull private String name;

  @NotNull private String kana;

  @NotNull private int price;

  @NotNull private String comment;

  @NotNull private String imageURL;

  @NotNull private int amount;

  public static class Builder {
    public ProductAttributes build() {
      final ProductAttributes attributes =
        new ProductAttributes(category, name, kana, price, comment, imageURL, amount);
      ValidateHelper.validate(this);
      return attributes;
    }
  }
}
