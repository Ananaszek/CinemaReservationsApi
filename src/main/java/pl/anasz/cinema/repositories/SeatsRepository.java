package pl.anasz.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.anasz.cinema.enities.Seat;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatsRepository extends JpaRepository<Seat,Long> {

    @Query("SELECT s.id FROM Seat s WHERE s.room.id = :roomId")
    List<Long> findSeatsIdsByRoomId(@Param("roomId") Long roomId);

    @Query("SELECT s.id FROM Seat s INNER JOIN s.reservations Reservation WHERE Reservation.screening.id = :screeningId")
    List<Long> findReservedSeatsByScreeningId(@Param("screeningId") Long screeningId);

    @Query("SELECT s FROM Seat s WHERE s.room.id = :roomId AND s.seatSignature = :signature")
    Optional<Seat> findSeatByRoomIdAndSignature(@Param("roomId") Long roomId, @Param("signature") String signature);
}
