package com.ibm.jp.ibmconsulting.icw.api.common.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AlphaNumericList.AlphaNumericListValidator.class})
public @interface AlphaNumericList {
  String message() default "ALPHANUMERIC,このフィールドでは英数字以外の値は入力できません。";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  public class AlphaNumericListValidator implements ConstraintValidator<AlphaNumericList, List<String>> {

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
      if (Objects.isNull(values)) {
        return true;
      }
      return values.stream().allMatch(v -> v.matches("^[0-9a-zA-Z]*$"));
    }
  }
}
