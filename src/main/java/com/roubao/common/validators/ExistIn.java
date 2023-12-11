package com.roubao.common.validators;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = ExistInValidatorForInteger.class)
public @interface ExistIn {

    String message() default "Parameter not in optional range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] intRange() default {};
}
