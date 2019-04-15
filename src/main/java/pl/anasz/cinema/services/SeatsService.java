package pl.anasz.cinema.services;

import pl.anasz.cinema.enities.Seat;

import java.util.List;
import java.util.Optional;

public interface SeatsService {

    List<Long> findSeatsIdsByRoomId(Long roomId);
    List<Long> findReservedSeatsByScreeningId(Long screeningId);
    Optional<Seat> findSeatByRoomIdAndSignature(Long roomId,String signature);
    Optional<Seat> findById(Long seatId);
    boolean existsById(Long seatId);
}
