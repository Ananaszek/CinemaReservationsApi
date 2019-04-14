package pl.anasz.cinema.servicesImpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.anasz.cinema.enities.Seat;
import pl.anasz.cinema.repositories.SeatsRepository;
import pl.anasz.cinema.services.SeatsService;

import java.util.List;
import java.util.Optional;

@Service
public class SeatsServiceImpl implements SeatsService {

    private SeatsRepository seatsRepository;

    @Autowired
    public SeatsServiceImpl(SeatsRepository seatsRepository) {
        this.seatsRepository = seatsRepository;
    }

    @Override
    public List<Long> findSeatsIdsByRoomId(Long roomId) {
        return seatsRepository.findSeatsIdsByRoomId(roomId);
    }

    @Override
    public List<Long> findReservedSeatsByScreeningId(Long screeningId) {
        return seatsRepository.findReservedSeatsByScreeningId(screeningId);
    }

    @Override
    public Optional<Seat> findSeatByRoomIdAndSignature(Long roomId, String signature) {
        return seatsRepository.findSeatByRoomIdAndSignature(roomId,signature);
    }

    @Override
    public Optional<Seat> findById(Long seatId) {
        return seatsRepository.findById(seatId);
    }

    @Override
    public boolean existsById(Long seatId) {
        return seatsRepository.existsById(seatId);
    }
}
