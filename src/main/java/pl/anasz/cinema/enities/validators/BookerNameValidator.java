package pl.anasz.cinema.enities.validators;

import pl.anasz.cinema.enities.User;
import pl.anasz.cinema.enities.constraints.BookerNameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BookerNameValidator implements ConstraintValidator<BookerNameConstraint, User> {

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        return Character.isUpperCase(user.getName().charAt(0));
    }
}
