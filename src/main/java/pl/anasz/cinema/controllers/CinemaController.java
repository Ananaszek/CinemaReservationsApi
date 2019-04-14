package pl.anasz.cinema.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import pl.anasz.cinema.enities.Reservation;
import pl.anasz.cinema.enities.Screening;
import pl.anasz.cinema.enities.User;
import pl.anasz.cinema.enities.Seat;

import pl.anasz.cinema.messageValidators.MessageValidatorService;
import pl.anasz.cinema.models.ReservationSummaryModel;
import pl.anasz.cinema.responses.ReservationResponse;
import pl.anasz.cinema.responses.SeatResponse;
import pl.anasz.cinema.services.SeatsService;
import pl.anasz.cinema.services.ScreeningsService;
import pl.anasz.cinema.services.UsersService;
import pl.anasz.cinema.services.RoomsService;
import pl.anasz.cinema.services.ReservationsService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("cinema")
public class CinemaController {
    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private SeatsService seatsService;
    private ScreeningsService screeningService;
    private UsersService userService;
    private RoomsService roomService;
    private ReservationsService reservationsService;
    private MessageValidatorService validationService;

    @Autowired
    public CinemaController(SeatsService seatsService, ScreeningsService screeningsService, MessageValidatorService validationService,
                            UsersService usersService, RoomsService roomService, ReservationsService reservationsService) {
        this.seatsService = seatsService;
        this.screeningService = screeningsService;
        this.userService = usersService;
        this.roomService = roomService;
        this.reservationsService = reservationsService;
        this.validationService = validationService;
    }

    @GetMapping(value = "/showMovies/{firstDate}/{secondDate}/")
    @ResponseBody
    public List<Object[]> showMoviesInInterval(@PathVariable("firstDate") String firstDate,
                                               @PathVariable("secondDate") String secondDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDateTime first = LocalDateTime.parse(firstDate, formatter);
        LocalDateTime second = LocalDateTime.parse(secondDate, formatter);

        return screeningService.findMoviesInInterval(first, second);
    }

    @GetMapping(value = "/selectScreening/{movieTitle}/{screeningTime}/")
    @ResponseBody
    public SeatResponse showScreeningDetails(@PathVariable("movieTitle") String movieTitle,
                                             @PathVariable("screeningTime") String screeningTime) {
        SeatResponse seatResponse = new SeatResponse();
        List<String> seatSignatures = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDateTime screeningDate = LocalDateTime.parse(screeningTime, formatter);
        Optional<Long> screeningIdOptional = screeningService.findRoomIdForScreening(movieTitle, screeningDate);
        Optional<Long> roomIdOptional = screeningService.findRoomIdForScreening(movieTitle, screeningDate);
        long screeningId = -1;
        long roomId = -1;

        if(screeningIdOptional.isPresent()){
            screeningId = screeningIdOptional.get();
        }

        if(roomIdOptional.isPresent()){
            roomId = roomIdOptional.get();
        }

        if (roomService.existsById(roomId)) {
            long roomNumber = roomService.findById(roomId).get().getNumber();
            seatResponse.setRoomNumber(roomNumber);

            List<Long> seatsIds = seatsService.findSeatsIdsByRoomId(roomId);
            List<Long> reservedSeatsIds = seatsService.findReservedSeatsByScreeningId(screeningId);
            seatsIds.forEach(seatId -> {
                if (!reservedSeatsIds.contains(seatId) && seatsService.existsById(seatId)) {
                    seatSignatures.add(seatsService.findById(seatId).get().getSeatSignature());
                }
            });
            seatResponse.setSeatsSignatures(seatSignatures);
        }
        return seatResponse;
    }

    @PostMapping(value = "/summary",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ReservationResponse getReservationSummary(@RequestBody ReservationSummaryModel summaryModel) {
        Reservation reservation = new Reservation();
        ReservationResponse reservationResponse = new ReservationResponse();
        Optional<Screening> screeningOptional = screeningService.findById(summaryModel.getScreeningId());
        Screening screening = new Screening();
        if (screeningOptional.isPresent()) {
            screening = screeningOptional.get();
        }

        if(LocalDateTime.now().isAfter(screening.getScreeningTime().minusMinutes(15))){
            reservationResponse.setValidationMessage("It's too late to reserve this screening");
            return reservationResponse;
        }

        reservation.setMovieTitle(screening.getMovie().getTitle());
        reservation.setExpirationDate(screening.getScreeningTime().minusMinutes(15));
        reservation.setNumberOfTickets(summaryModel.getSeatsSignatures().size());

        User booker = new User();
        booker.setName(summaryModel.getBookerName());
        booker.setSurname(summaryModel.getBookerSurname());

        reservation.setBooker(booker);
        reservation.setScreening(screening);

        BigDecimal amount = calculateAmount(summaryModel.getTicketTypes());
        reservation.setAmount(amount);

        List<Seat> seatList = new ArrayList<>();
        Optional<Long> roomIdOptional = screeningService.findRoomIdByScreeningId(summaryModel.getScreeningId());

        summaryModel.getSeatsSignatures().forEach(seatSignature -> {
            long roomId = -1;
            if(roomIdOptional.isPresent()){
                roomId = roomIdOptional.get();
            }
            Optional<Seat> seatOptional = seatsService.findSeatByRoomIdAndSignature(roomId, seatSignature);
            if (seatOptional.isPresent()) {
                Seat seat = seatOptional.get();
                seat.setReservations(Collections.singletonList(reservation));
                seatList.add(seat);
            }

        });

        reservation.setSeats(seatList);
        Optional<ReservationResponse> validation = validationService.validateReservation(reservation);
        if(validation.isPresent()){
            return validation.get();
        }

        userService.save(booker);
        reservationsService.save(reservation);

        reservationResponse.setAmount(amount);
        reservationResponse.setReservationExpirationDate(screening.getScreeningTime().minusMinutes(15));
        reservationResponse.setValidationMessage("SUCCESS!");

        return reservationResponse;
    }

    private BigDecimal calculateAmount(List<String> ticketTypes) {
        double adultTicket = 25;
        double studentTicket = 18;
        double childTicket = 12.50;
        double amount = 0;
        for (String t : ticketTypes) {
            switch (t) {
                case "A":
                    amount += adultTicket;
                    break;
                case "S":
                    amount += studentTicket;
                    break;
                case "C":
                    amount += childTicket;
                    break;
            }
        }

        return BigDecimal.valueOf(amount);
    }
}