package pl.anasz.cinema.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReservationResponse {

    private BigDecimal amount;
    private LocalDateTime reservationExpirationDate;
    private String validationMessage;
}
