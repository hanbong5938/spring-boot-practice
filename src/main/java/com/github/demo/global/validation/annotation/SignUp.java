package com.github.demo.global.validation.annotation;

import com.github.demo.global.validation.validator.SignUpValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SignUpValidator.class)
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SignUp {
    String message() default "Sign Up is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
