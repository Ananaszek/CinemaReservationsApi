package pl.anasz.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.anasz.cinema.enities.Movie;

@Repository
public interface MovieReposiotory extends JpaRepository<Movie, Long> {
}
