package pl.anasz.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.anasz.cinema.enities.Cinema;

@Repository
public interface CinemaRepo extends JpaRepository<Cinema, Long> {
}
