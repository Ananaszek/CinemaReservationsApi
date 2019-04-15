package pl.anasz.cinema.services;

import pl.anasz.cinema.enities.Room;

import java.util.Optional;

public interface RoomsService {

    boolean existsById(Long roomId);
    Optional<Room> findById(Long roomId);
}
