package com.ibm.jp.ibmconsulting.icw.api.ui.stocks;

import javax.validation.constraints.NotNull;
import com.ibm.jp.ibmconsulting.icw.api.ui.validation.BeanValidationTarget;
import com.ibm.jp.ibmconsulting.icw.api.ui.validation.annotation.Name;
import lombok.Data;

@Data
public class PutRequestBody implements BeanValidationTarget {
  @Name("amount")
  @NotNull
  private Integer amount;
}
