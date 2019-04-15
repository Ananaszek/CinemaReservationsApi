package pl.anasz.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.anasz.cinema.enities.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
}
