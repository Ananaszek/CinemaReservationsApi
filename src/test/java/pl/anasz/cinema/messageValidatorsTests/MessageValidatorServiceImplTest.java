package pl.anasz.cinema.messageValidatorsTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pl.anasz.cinema.enities.Reservation;
import pl.anasz.cinema.enities.Room;
import pl.anasz.cinema.enities.Seat;
import pl.anasz.cinema.enities.User;
import pl.anasz.cinema.messageValidators.MessageValidatorService;
import pl.anasz.cinema.messageValidators.MessageValidatorServiceImpl;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class MessageValidatorServiceImplTest {

    @TestConfiguration
    static class MessageValidatorServiceImplTestContextConfiguration {

        public MessageValidatorService messageValidatorService(){
            return new MessageValidatorServiceImpl();
        }
    }

    private ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
    private Validator validator = vf.getValidator();
    private Reservation reservation = new Reservation();
    private User booker = new User();
    private Room room = new Room();
    private Seat seat = new Seat();
    private Seat seat2 = new Seat();

    private MessageValidatorService messageValidatorService;

    @Test
    public void reservationSeatsAreNull() throws Exception{
        booker.setName("Jan");
        booker.setSurname("Tchórz");
        reservation.setBooker(booker);

        reservation.setAmount(BigDecimal.valueOf(50));
        reservation.setNumberOfTickets(2);
        reservation.setExpirationDate(LocalDateTime.now().plusDays(5));

        Set<ConstraintViolation<Reservation>> constraintViolations = validator
                .validate(reservation);
        assertThat("unexpected size of constraint violations",
                constraintViolations.size(), equalTo(1));

        assertEquals("You have to reserve at least 1 seat!",constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void reservationSeatsAreNotNeighbouring() throws Exception{
        booker.setName("Jan");
        booker.setSurname("Tchórz");
        reservation.setBooker(booker);

        room.setNumber(1);

        seat.setSeatSignature("A1");
        seat.setRoom(room);
        seat2.setSeatSignature("B2");
        seat.setRoom(room);

        List<Seat> seats = new ArrayList<>();
        seats.add(seat);
        seats.add(seat2);

        reservation.setSeats(seats);
        reservation.setAmount(BigDecimal.valueOf(50));
        reservation.setNumberOfTickets(2);
        reservation.setExpirationDate(LocalDateTime.now().plusDays(5));

        Set<ConstraintViolation<Reservation>> constraintViolations = validator
                .validate(reservation);
        assertThat("unexpected size of constraint violations",
                constraintViolations.size(), equalTo(1));

        assertEquals("You have to select neighbouring seatsSignatures!",constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void bookerNameStartsWithSmallLetter(){
        booker.setName("olek");
        booker.setSurname("Nowak");
        reservation.setBooker(booker);

        room.setNumber(1);

        seat.setSeatSignature("A1");
        seat.setRoom(room);
        seat2.setSeatSignature("A2");
        seat.setRoom(room);

        List<Seat> seats = new ArrayList<>();
        seats.add(seat);
        seats.add(seat2);

        reservation.setSeats(seats);

        Set<ConstraintViolation<Reservation>> constraintViolations = validator
                .validate(reservation);
        assertThat("unexpected size of constraint violations",
                constraintViolations.size(), equalTo(1));

        assertEquals("Name has to start with capital letter!",constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void bookerSurnameStartsWithSmallLetter(){
        booker.setName("Olek");
        booker.setSurname("Kowalski-nowak");
        reservation.setBooker(booker);

        room.setNumber(1);

        seat.setSeatSignature("A1");
        seat.setRoom(room);
        seat2.setSeatSignature("A2");
        seat.setRoom(room);

        List<Seat> seats = new ArrayList<>();
        seats.add(seat);
        seats.add(seat2);

        reservation.setSeats(seats);

        Set<ConstraintViolation<Reservation>> constraintViolations = validator
                .validate(reservation);
        assertThat("unexpected size of constraint violations",
                constraintViolations.size(), equalTo(1));

        assertEquals("Surname has to start with capital letter!",constraintViolations.iterator().next().getMessage());
    }
}
