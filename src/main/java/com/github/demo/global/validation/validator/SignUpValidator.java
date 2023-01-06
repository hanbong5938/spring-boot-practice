package com.github.demo.global.validation.validator;

import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.global.validation.annotation.SignUp;
import com.github.demo.global.validation.common.CommonValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jetbrains.annotations.NotNull;

public class SignUpValidator extends CommonValidator implements ConstraintValidator<SignUp, AccountDto.SignUpRequest> {
    @Override
    public void initialize(SignUp constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final AccountDto.SignUpRequest value, final ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context
                .buildConstraintViolationWithTemplate("Invalid data")
                .addConstraintViolation();
        try {
            return switch (value.accountType()) {
                case ADMIN -> this.checkAdmin(value);
                case MEMBER -> this.checkMember(value);
                default -> false;
            };
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkAdmin(final AccountDto.@NotNull SignUpRequest value) {
        return this.isaBoolean(
                this.nonEmpty(value.email().getValue()),
                this.nonEmpty(value.password().getValue())
        );
    }

    private boolean checkMember(final AccountDto.@NotNull SignUpRequest value) {
        return this.isaBoolean(
                this.nonEmpty(value.email().getValue()),
                this.nonEmpty(value.password().getValue())
        );
    }
}
