package com.ibm.jp.ibmconsulting.icw.api.common.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/** リスト要素が指定した複数の選択肢のいずれかであることを判定するためのアノテーション。nullを許容しない場合は別途@NonNullを付与すること　 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SelectiveItemList.SelectiveItemListValidator.class})
public @interface SelectiveItemList {

  String message() default "SELECTIVE_ITEM,期待とは異なる値が入力されました。";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String[] items() default "";

  public class SelectiveItemListValidator
      implements ConstraintValidator<SelectiveItemList, List<String>> {

    private String[] items;

    @Override
    public void initialize(SelectiveItemList selectiveItemList) {
      items = selectiveItemList.items();
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
      if (Objects.isNull(values)) {
        return true;
      }
      return values.stream().allMatch(v -> Arrays.asList(items).contains(v));
    }
  }
}
