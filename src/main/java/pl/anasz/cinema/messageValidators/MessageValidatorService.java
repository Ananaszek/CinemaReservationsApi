package pl.anasz.cinema.messageValidators;

import pl.anasz.cinema.enities.Reservation;
import pl.anasz.cinema.responses.ReservationResponse;

import java.util.Optional;

public interface MessageValidatorService {

    Optional<ReservationResponse> validateReservation(Reservation reservation);
}
