package pl.anasz.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.anasz.cinema.enities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
