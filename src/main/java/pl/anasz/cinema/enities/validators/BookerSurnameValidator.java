package pl.anasz.cinema.enities.validators;

import pl.anasz.cinema.enities.User;
import pl.anasz.cinema.enities.constraints.BookerSurnameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BookerSurnameValidator implements ConstraintValidator<BookerSurnameConstraint, User> {
    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if(!user.getSurname().contains("-")){
            return Character.isUpperCase(user.getSurname().charAt(0));
        } else {
            String secondSurname = user.getSurname().substring(user.getSurname().indexOf('-')+1);
            return Character.isUpperCase(user.getSurname().charAt(0)) && Character.isUpperCase(secondSurname.charAt(0));
        }
    }
}
