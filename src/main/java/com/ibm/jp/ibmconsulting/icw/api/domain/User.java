package com.ibm.jp.ibmconsulting.icw.api.domain;

import javax.validation.constraints.NotNull;

import com.ibm.jp.ibmconsulting.icw.api.common.validation.ValidateHelper;

import lombok.Getter;

@Getter
public class User {
  @NotNull private String id;

  @NotNull private UserAttributes attributes;

  public User(String id, UserAttributes attributes) {
    this.id = id;
    this.attributes = attributes;
    ValidateHelper.validate(this);
  }
}
