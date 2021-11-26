package com.ibm.jp.ibmconsulting.icw.api.ui.validation;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.ibm.jp.ibmconsulting.icw.api.ui.error.BadRequestParamsException;
import com.ibm.jp.ibmconsulting.icw.api.ui.error.Error;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.ibm.jp.ibmconsulting.icw.api.ui.validation.annotation.Name;

public final class BeanValidateHelper {
  private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  private static final Validator validator = factory.getValidator();
  private static final int VIOLATION_MESSAGE_COLUMNS = 2;

  private BeanValidateHelper() {}

  /** Validation Beanによる各項目の検証を行う. 検証の結果、全ての項目が有効であれば、空のListを返す。 */
  private static Set<Error> validate(final BeanValidationTarget target) {
    final Set<Error> errors = new HashSet<>();

    /** 独自の検証があれば、実行する */
    if (target instanceof CustomValidationTarget) {
      errors.addAll(((CustomValidationTarget) target).customValidate());
    }

    final Set<ConstraintViolation<BeanValidationTarget>> violations = validator.validate(target);
    if (violations.isEmpty()) {
      return errors;
    }

    for (final ConstraintViolation<BeanValidationTarget> violation : violations) {
      try {
        final String[] tmp = violation.getMessage().split(",");
        if (tmp.length != VIOLATION_MESSAGE_COLUMNS) {
          throw new ValidationRuntimeException("メッセージ定義が不正です。メッセージはカンマ区切り形式で要素が2つ必要です。");
        }
        final String code = tmp[0];
        final String message = tmp[1];
        final String field =
            target
                .getClass()
                .getDeclaredField(violation.getPropertyPath().toString())
                .getAnnotation(Name.class)
                .value();
        final Error error = new Error(field, code, message);
        errors.add(error);

      } catch (NoSuchFieldException | SecurityException e) {
        throw new ValidationRuntimeException("フォーマット不正情報の抽出中に予期しないエラーが発生しました。", e);
      }
    }
    return errors;
  }

  /**
   * Validation Beanによる各項目の検証を行う
   *
   * <p>検証の結果、無効な項目があればBadRequestParamsExceptionを返す
   *
   * @throws BadRequestParamsException
   */
  public static void validate(final BeanValidationTarget... targets) {
    final Set<Error> errors = new HashSet<>();
    for (final BeanValidationTarget target : targets) {
      if (Objects.nonNull(target)) {
        errors.addAll(validate(target));
      }
    }
    if (!errors.isEmpty()) {
      throw new BadRequestParamsException(errors);
    }
  }
}
