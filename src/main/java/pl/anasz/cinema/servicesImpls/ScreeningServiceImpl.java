package pl.anasz.cinema.servicesImpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.anasz.cinema.enities.Screening;
import pl.anasz.cinema.repositories.ScreeningRepository;
import pl.anasz.cinema.services.ScreeningsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScreeningServiceImpl implements ScreeningsService {

    private ScreeningRepository screeningRepository;

    @Autowired
    public ScreeningServiceImpl(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    @Override
    public Optional<Long> findRoomIdForScreening(String movieTitle, LocalDateTime screeningTime) {
        return screeningRepository.findRoomIdForScreening(movieTitle,screeningTime);
    }

    @Override
    public List<Object[]> findMoviesInInterval(LocalDateTime date1, LocalDateTime date2) {
        return screeningRepository.findMoviesInInterval(date1,date2);
    }

    @Override
    public Optional<Long> findRoomIdByScreeningId(Long screeningId) {
        return screeningRepository.findRoomIdByScreeningId(screeningId);
    }

    @Override
    public Optional<Screening> findById(Long screeningId) {
        return screeningRepository.findById(screeningId);
    }
}
