package com.roubao.common.validators;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 判断参数是否存在于配置中（Integer解析器）
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/10
 **/
public class ExistInValidatorForInteger implements ConstraintValidator<ExistIn, Integer> {

    private int[] intRange;

    @Override
    public void initialize(ExistIn constraintAnnotation) {
        intRange = constraintAnnotation.intRange();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (intRange == null || intRange.length == 0 || value == null) {
            return false;
        } else {
            for (int i : intRange) {
                if (value.equals(i)) {
                    return true;
                }
            }
        }
        return false;
    }
}
