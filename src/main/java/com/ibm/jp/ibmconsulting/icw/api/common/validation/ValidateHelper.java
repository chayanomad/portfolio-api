package com.ibm.jp.ibmconsulting.icw.api.common.validation;

import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

public final class ValidateHelper {
  private static final Validator validator =
      Validation.buildDefaultValidatorFactory().getValidator();
  
  private ValidateHelper() {}

  public static <T> void validate(T object) {
    final Set<ConstraintViolation<T>> violations = validator.validate(object);
    if (violations.isEmpty()) {
      return;
    }
    final String msg =
        violations
            .stream()
            .map(
                v ->
                    String.format(
                        "%s.%s(%s)",
                        object.getClass().getName(), v.getPropertyPath(), v.getMessage()))
            .collect(Collectors.joining(","));
    throw new ValidationException(msg);
  }
}
