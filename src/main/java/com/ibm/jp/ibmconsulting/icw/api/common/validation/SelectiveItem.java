package com.ibm.jp.ibmconsulting.icw.api.common.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import java.util.stream.Stream;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/** フィールドが指定した複数の選択肢のうちのひとつであるか判定するためのアノテーション。nullを許容しない場合は別途@NonNullを付与すること　 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SelectiveItem.SelectiveItemValidator.class})
public @interface SelectiveItem {

  String message() default "SELECTIVE_ITEM,期待とは異なる値が入力されました。";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String[] items() default "";

  @Target({ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface List {
    SelectiveItem[] value();
  }

  public class SelectiveItemValidator implements ConstraintValidator<SelectiveItem, String> {

    private String[] items;

    @Override
    public void initialize(SelectiveItem selectiveItem) {
      items = selectiveItem.items();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      if (Objects.isNull(value)) {
        return true;
      }
      return Stream.of(items).anyMatch(i -> i.equals(value));
    }
  }
}
