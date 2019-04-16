package com.example.granny.validation.annotations;

import com.example.granny.constants.GlobalConstants;
import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Length(max = 80, message = ValidMessageSubject.SUBJECT_TOO_LONG)
@NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidMessageSubject {

    String SUBJECT_TOO_LONG = "Subject should be less than 80 characters long";

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
