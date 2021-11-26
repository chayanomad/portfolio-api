package com.ibm.jp.ibmconsulting.icw.api.ui.validation;

import java.util.Set;
import com.ibm.jp.ibmconsulting.icw.api.ui.error.Error;

public interface CustomValidationTarget {
  Set<Error> customValidate();
}
