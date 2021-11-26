package com.ibm.jp.ibmconsulting.icw.api.domain;

import lombok.Getter;

public class ItemNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  @Getter private final String id;
  
  public ItemNotFoundException(String id) {
    super();
    this.id = id;
  }
}
