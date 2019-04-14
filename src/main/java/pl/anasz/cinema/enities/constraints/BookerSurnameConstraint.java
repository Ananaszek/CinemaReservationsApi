package pl.anasz.cinema.enities.constraints;

import pl.anasz.cinema.enities.validators.BookerSurnameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = BookerSurnameValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BookerSurnameConstraint {
    String message() default "Invalid booker surname";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
