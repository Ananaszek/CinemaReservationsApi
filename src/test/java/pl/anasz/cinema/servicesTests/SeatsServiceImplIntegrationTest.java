package pl.anasz.cinema.servicesTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.anasz.cinema.enities.Cinema;
import pl.anasz.cinema.enities.Reservation;
import pl.anasz.cinema.enities.Room;
import pl.anasz.cinema.enities.Seat;
import pl.anasz.cinema.repositories.SeatsRepository;
import pl.anasz.cinema.services.SeatsService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeatsServiceImplIntegrationTest {

    @Autowired
    private SeatsService seatsService;
    @MockBean
    private SeatsRepository seatsRepository;

    private Seat seat = new Seat();
    private Room room = new Room();
    private Cinema cinema = new Cinema();
    private Reservation reservation = new Reservation();

    @Before
    public void createTestEntities(){
        cinema.setName("CinemaCity");

        room.setId(5);
        room.setNumber(8);
        room.setCinema(cinema);

        seat.setId(40);
        seat.setSeatSignature("A1");
        seat.setRoom(room);

        Mockito.when(seatsRepository.findSeatsIdsByRoomId(room.getId()))
                .thenReturn(Collections.singletonList(seat.getId()));

        Mockito.when(seatsRepository.findSeatByRoomIdAndSignature(room.getId(),seat.getSeatSignature()))
                .thenReturn(Optional.ofNullable(seat));

        Mockito.when(seatsRepository.findById(seat.getId()))
                .thenReturn(Optional.ofNullable(seat));
    }

    @Test
    public void whenValidByRoomId_thenSeatsIdsShouldBeFound(){
        long roomId = 5;

        List<Long> foundIds = seatsService.findSeatsIdsByRoomId(roomId);

        Assert.assertEquals((long) foundIds.get(0), seat.getId());
    }

    @Test
    public void whenValidRoomIdAndSignature_thenReturnSeat(){
        long roomId = 5;
        String signature = "A1";

        Optional<Seat> foundSeat = seatsService.findSeatByRoomIdAndSignature(roomId,signature);

        Assert.assertEquals(foundSeat.get(), seat);
        Assert.assertEquals(foundSeat.get().getRoom().getId(), roomId);
        Assert.assertEquals(foundSeat.get().getSeatSignature(), signature);
    }

    @Test
    public void whenValidSeatId_thenSeatShouldBeReturned(){
        long seatId = 40;

        Optional<Seat> foundSeat = seatsService.findById(seatId);

        //then
        Assert.assertEquals(foundSeat.get().getId(), seatId);
    }
}
