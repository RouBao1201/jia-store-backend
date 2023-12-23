package com.roubao.common.validators;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 判断参数是否存在于配置中（String解析器）
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/10
 **/
public class ExistInValidatorForString implements ConstraintValidator<ExistIn, String> {

    private String[] strRange;

    @Override
    public void initialize(ExistIn constraintAnnotation) {
        strRange = constraintAnnotation.strRange();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (strRange == null || strRange.length == 0 || value == null) {
            return false;
        } else {
            for (String s : strRange) {
                if (value.equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }
}
