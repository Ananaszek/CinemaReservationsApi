package pl.anasz.cinema.repositoriesTests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.anasz.cinema.enities.Seat;
import pl.anasz.cinema.enities.Room;
import pl.anasz.cinema.enities.Cinema;
import pl.anasz.cinema.enities.Reservation;
import pl.anasz.cinema.enities.Screening;
import pl.anasz.cinema.enities.Movie;
import pl.anasz.cinema.enities.User;
import pl.anasz.cinema.repositories.SeatsRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SeatsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private SeatsRepository seatsRepository;

    public SeatsRepositoryTest() {
    }

    private Seat seat = new Seat();
    private Room room = new Room();
    private Cinema cinema = new Cinema();
    private Reservation reservation = new Reservation();

    @Before
    public void createTestEntities(){
        cinema.setName("Helios");
        entityManager.persist(cinema);

        room.setNumber(1);
        room.setCinema(cinema);
        entityManager.persist(room);

        seat.setSeatSignature("A1");
        seat.setRoom(room);
        entityManager.persist(seat);
    }

    @After
    public void deleteEntities(){
        entityManager.remove(cinema);
        entityManager.remove(room);
        entityManager.remove(seat);
    }

    @Test
    public void whenFindSeatsIdsByRoomId_thenReturnSeatsIds(){
        //when
        List<Long> foundIds = seatsRepository.findSeatsIdsByRoomId(seat.getRoom().getId());

        //then
        Assert.assertEquals((long) foundIds.get(0), seat.getId());
    }

    @Test
    public void whenFindReservedSeatsByScreeningId_thenReturnSeatsIds(){
        //given
        Seat seat2 = new Seat();
        seat2.setSeatSignature("A2");
        seat2.setRoom(room);
        entityManager.persist(seat2);

        List<Seat> seats = new ArrayList<>();
        seats.add(seat);
        seats.add(seat2);
        Screening screening = new Screening();

        Movie movie = new Movie();
        movie.setTitle("Chilling adventures of Sabrina");
        entityManager.persist(movie);

        screening.setRoomId(room.getId());
        screening.setScreeningTime(LocalDateTime.of(2019, 4,12,12, 0, 0));
        screening.setMovie(movie);
        entityManager.persist(screening);

        User booker = new User();
        booker.setName("Janusz");
        booker.setSurname("Kowalski");
        entityManager.persist(booker);

        reservation.setAmount(BigDecimal.valueOf(50));
        reservation.setMovieTitle(movie.getTitle());
        reservation.setBooker(booker);
        reservation.setExpirationDate(screening.getScreeningTime().minusMinutes(15));
        reservation.setNumberOfTickets(2);
        reservation.setScreening(screening);
        reservation.setSeats(seats);
        entityManager.persist(reservation);
        entityManager.flush();

        //when
        List<Long> foundIds = seatsRepository.findReservedSeatsByScreeningId(reservation.getScreening().getId());

        //then
        Assert.assertEquals(foundIds.size(),0);
    }

    @Test
    public void whenFindSeatByRoomIdAndSignature_thenReturnSeat(){
        //when
        Optional<Seat> foundSeat = seatsRepository.findSeatByRoomIdAndSignature(seat.getRoom().getId(),seat.getSeatSignature());

        //then
        Assert.assertEquals(foundSeat.get(), seat);
    }
}
