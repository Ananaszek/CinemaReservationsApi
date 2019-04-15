package pl.anasz.cinema.models;

import lombok.Data;

import java.util.List;

@Data
public class ReservationSummaryModel {

    private long screeningId;
    private List<String> seatsSignatures;
    private List<String> ticketTypes;
    private String bookerName;
    private String bookerSurname;
}
