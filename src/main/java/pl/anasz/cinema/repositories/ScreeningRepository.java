package pl.anasz.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.anasz.cinema.enities.Screening;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

      @Query("SELECT sc.roomId FROM Screening sc WHERE sc.movie.title = :movieId AND sc.screeningTime = :screeningTime")
      Optional<Long> findRoomIdForScreening(@Param("movieId") String movieTitle, @Param("screeningTime")LocalDateTime screeningTime);

      @Query("SELECT sc.movie.title,sc.screeningTime FROM Screening sc WHERE sc.screeningTime BETWEEN :date_1 AND :date_2 GROUP BY sc.movie.title,sc.screeningTime")
      List<Object[]> findMoviesInInterval(@Param("date_1") LocalDateTime date1, @Param("date_2") LocalDateTime date2);

      @Query("SELECT sc.roomId FROM Screening sc WHERE sc.id = :screeningId")
      Optional<Long> findRoomIdByScreeningId(@Param("screeningId")Long screeningId);
}
