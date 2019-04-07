package com.example.granny.validation.annotations;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.validation.validators.LastNameConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = LastNameConstraintValidator.class)
@Documented
public @interface ValidLastName {

    String LAST_NAME_ERROR_MESSAGE = "Last name should be between 1 and 20 characters";
    String LAST_NAME_ONLY_LETTERS = "Last name should contain only letters";

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
