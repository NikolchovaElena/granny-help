package com.example.granny.validation.annotations;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.validation.validators.PasswordConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Documented
public @interface ValidPassword {

    String PASSWORD_ERROR_MESSAGE = "Password should contain at least one uppercase, lowercase and number";
    String PASSWORD_LENGTH_ERROR_MESSAGE = "Password should be between 6 and 15 characters long";

    String message() default PASSWORD_ERROR_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
