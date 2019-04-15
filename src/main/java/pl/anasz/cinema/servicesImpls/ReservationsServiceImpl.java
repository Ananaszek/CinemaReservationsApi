package pl.anasz.cinema.servicesImpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.anasz.cinema.enities.Reservation;
import pl.anasz.cinema.repositories.ReservationRepository;
import pl.anasz.cinema.services.ReservationsService;

@Service
public class ReservationsServiceImpl implements ReservationsService {

    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationsServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
    }
}
