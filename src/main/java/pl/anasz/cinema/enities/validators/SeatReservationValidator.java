package pl.anasz.cinema.enities.validators;

import pl.anasz.cinema.enities.Seat;
import pl.anasz.cinema.enities.constraints.SeatReservationConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class SeatReservationValidator implements ConstraintValidator<SeatReservationConstraint,List<Seat>> {
    @Override
    public boolean isValid(List<Seat> seats, ConstraintValidatorContext constraintValidatorContext) {
        if(seats.size()>=2){
        for(int i = 0; i < seats.size(); i++){
            for(int j = i+1; j < seats.size(); j++){
                if(areSeatsCorrect(seats, i, j)){
                    return true;
                }
            }
        }
        }else return seats.size() == 1;
        return false;
    }

    private boolean areSeatsCorrect(List<Seat> seats, int i, int j) {
        return seats.get(i).getSeatSignature().charAt(0)==seats.get(j).getSeatSignature().charAt(0)&&
                seats.get(i).getSeatSignature().charAt(1)+1 == seats.get(j).getSeatSignature().charAt(1);
    }
}
