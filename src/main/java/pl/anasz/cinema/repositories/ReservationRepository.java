package pl.anasz.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.anasz.cinema.enities.Reservation;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
