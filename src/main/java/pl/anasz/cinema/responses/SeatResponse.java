package pl.anasz.cinema.responses;

import lombok.Data;

import java.util.List;

@Data
public class SeatResponse {

    private long roomNumber;
    private List<String> seatsSignatures;
}
