package com.ibm.jp.ibmconsulting.icw.api.ui.products;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.jp.ibmconsulting.icw.api.common.validation.ValidateHelper;
import com.ibm.jp.ibmconsulting.icw.api.domain.Product;
import com.ibm.jp.ibmconsulting.icw.api.domain.ProductAttributes;

import lombok.Getter;

@Getter
public class ProductResponse {
  @NotNull private final String id;
  
  @NotNull private final String category;

  @NotNull private final String name;

  @NotNull private final String kana;

  @NotNull private final int price;

  @NotNull private final String comment;

  @NotNull
  @JsonProperty("image_url")
  private final String imageURL;

  @NotNull
  private final int stock;

  public ProductResponse(Product item) {
    final ProductAttributes attributes = item.getAttributes();
    this.id = item.getId();
    this.category = attributes.getCategory().name();
    this.name = attributes.getName();
    this.kana = attributes.getKana();
    this.price = attributes.getPrice();
    this.comment = attributes.getComment();
    this.imageURL = attributes.getImageURL();
    this.stock = attributes.getAmount();
    ValidateHelper.validate(this);
  }
}
