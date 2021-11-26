package com.ibm.jp.ibmconsulting.icw.api.domain;

import javax.validation.constraints.NotNull;

import com.ibm.jp.ibmconsulting.icw.api.common.validation.ValidateHelper;

import lombok.Getter;

@Getter
public class Item {
  @NotNull private String id;

  @NotNull private ItemAttributes attributes;

  public Item(String id, ItemAttributes attributes) {
    this.id = id;
    this.attributes = attributes;
    ValidateHelper.validate(this);
  }
}
