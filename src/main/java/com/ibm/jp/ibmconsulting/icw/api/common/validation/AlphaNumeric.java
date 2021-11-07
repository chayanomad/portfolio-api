package com.ibm.jp.ibmconsulting.icw.api.common.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AlphaNumeric.AlphaNumericValidator.class})
public @interface AlphaNumeric {
  String message() default "ALPHANUMERIC,このフィールドでは英数字以外の値は入力できません。";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  public class AlphaNumericValidator implements ConstraintValidator<AlphaNumeric, String> {

    @Override
    public void initialize(AlphaNumeric constraintAnnotation) {
      ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      return Objects.isNull(value) || value.matches("^[0-9a-zA-Z]*$");
    }
  }
}
