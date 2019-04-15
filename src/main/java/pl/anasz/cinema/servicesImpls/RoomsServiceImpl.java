package pl.anasz.cinema.servicesImpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.anasz.cinema.enities.Room;
import pl.anasz.cinema.repositories.RoomRepository;
import pl.anasz.cinema.services.RoomsService;

import java.util.Optional;

@Service
public class RoomsServiceImpl implements RoomsService {

    private RoomRepository roomRepository;

    @Autowired
    public RoomsServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public boolean existsById(Long roomId) {
        return roomRepository.existsById(roomId);
    }

    @Override
    public Optional<Room> findById(Long roomId) {
        return roomRepository.findById(roomId);
    }
}
