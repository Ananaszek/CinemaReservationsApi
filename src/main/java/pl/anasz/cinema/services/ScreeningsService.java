package pl.anasz.cinema.services;

import pl.anasz.cinema.enities.Screening;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningsService {

    Optional<Long> findRoomIdForScreening(String movieTitle, LocalDateTime screeningTime);
    List<Object[]> findMoviesInInterval(LocalDateTime date1, LocalDateTime date2);
    Optional<Long> findRoomIdByScreeningId(Long screeningId);
    Optional<Screening> findById(Long screeningId);
}
