package com.example.granny.validation.annotations;

import com.example.granny.constants.GlobalConstants;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
@Email(message = ValidEmail.EMAIL_ERROR_MESSAGE)
@Size(max = 254, message = ValidEmail.EMAIL_TOO_LONG)
//@Pattern(regexp = ".+@.+\\..+", message = ValidEmail.EMAIL_ERROR_MESSAGE)
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidEmail {

    String EMAIL_ERROR_MESSAGE = "Please provide a valid email address";
    String EMAIL_TOO_LONG = "Email is too long";

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
