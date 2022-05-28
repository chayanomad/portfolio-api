package com.ibm.jp.ibmconsulting.icw.api.domain;

import lombok.Getter;

public class UserNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  @Getter private final String id;
  
  public UserNotFoundException(String id) {
    super();
    this.id = id;
  }
}
