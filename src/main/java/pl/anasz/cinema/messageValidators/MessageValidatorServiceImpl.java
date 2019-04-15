package pl.anasz.cinema.messageValidators;

import org.springframework.stereotype.Component;
import pl.anasz.cinema.enities.Reservation;
import pl.anasz.cinema.responses.ReservationResponse;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;

@Component
public class MessageValidatorServiceImpl implements MessageValidatorService{

    private ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
    private Validator validator = vf.getValidator();

    @Override
    public Optional<ReservationResponse> validateReservation(Reservation reservation) {
        return validate(reservation);
    }

    private Optional<ReservationResponse> validate(Reservation reservation) {
        Optional<String> validationMessage = validateIncomingReservation(reservation);
        if(validationMessage.isPresent()){
            ReservationResponse response = new ReservationResponse();
            response.setValidationMessage(validationMessage.get());
            return Optional.of(response);
        }

        return Optional.empty();
    }

    private <T> Optional<String> validateIncomingReservation(T income) {
        Set<ConstraintViolation<T>> violation = validator.validate(income);
        List<String> list = new ArrayList<>();
        if(!violation.isEmpty()){
            violation.forEach(v -> list.add(v.getMessage()));
            Collections.sort(list);
            return Optional.of(list.get(0));
        }
        return Optional.empty();
    }
}
