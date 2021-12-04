package com.ibm.jp.ibmconsulting.icw.api.domain;

import javax.validation.constraints.NotNull;

import com.ibm.jp.ibmconsulting.icw.api.common.validation.ValidateHelper;

import lombok.Getter;

@Getter
public class Product {
  @NotNull private String id;

  @NotNull private ProductAttributes attributes;

  public Product(String id, ProductAttributes attributes) {
    this.id = id;
    this.attributes = attributes;
    ValidateHelper.validate(this);
  }
}
