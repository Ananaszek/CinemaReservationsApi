package pl.anasz.cinema.servicesTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import pl.anasz.cinema.enities.Movie;
import pl.anasz.cinema.enities.Room;
import pl.anasz.cinema.enities.Screening;
import pl.anasz.cinema.repositories.ScreeningRepository;
import pl.anasz.cinema.services.ScreeningsService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScreeningsServiceImplIntegrationTest {

    @Autowired
    private ScreeningsService screeningsService;
    @Autowired
    ApplicationContext context;

    @MockBean
    private ScreeningRepository screeningRepository;

    Movie movie = new Movie();
    Screening screening = new Screening();
    Room room = new Room();

    @Before
    public void createTestEntities(){
        movie.setTitle("Chilling adventures of Sabrina");
        movie.setId(10);
        screening.setMovie(movie);

        room.setId(10);
        room.setNumber(5);
        screening.setId(10);
        screening.setRoomId(room.getId());
        screening.setScreeningTime(LocalDateTime.of(2019, 4,12,12, 0, 0));
        screening.setMovie(movie);


        Mockito.when(screeningRepository.findRoomIdByScreeningId(screening.getId()))
                .thenReturn(Optional.of(room.getId()));

        Mockito.when(screeningRepository.findRoomIdForScreening(screening.getMovie().getTitle(),screening.getScreeningTime()))
                .thenReturn(Optional.of(room.getId()));

        Mockito.when(screeningRepository.findById(screening.getId()))
                .thenReturn(Optional.ofNullable(screening));
    }

    @Test
    public void whenValidScreeningId_thenRoomShouldBeFound(){
        long screeningId = 10;
        Optional<Long> foundRoomIdOptional = screeningsService.findRoomIdByScreeningId(screeningId);
        long foundRoomId = -1;
        if(foundRoomIdOptional.isPresent()){
            foundRoomId = foundRoomIdOptional.get();
        }

        assertThat(foundRoomId).isEqualTo(screeningId);
    }

    @Test
    public void whenValidScreeningDetails_thenRoomIdShouldBeFound(){
        String movieTitle = "Chilling adventures of Sabrina";
        LocalDateTime screeningDate = LocalDateTime.of(2019, 4,12,12, 0, 0);

        Optional<Long> foundRoomIdOptional = screeningsService.findRoomIdForScreening(movieTitle,screeningDate);
        long foundRoomId = -1;

        if(foundRoomIdOptional.isPresent()){
            foundRoomId = foundRoomIdOptional.get();
        }

        assertThat(foundRoomId).isEqualTo(room.getId());
    }

    @Test
    public void whenValidScreeningId_thenScreeningOptionalShouldBeFound(){
        long id = 10;

        Optional<Screening> screening = screeningsService.findById(id);

        assertThat(screening.get().getId()).isEqualTo(id);
    }
}
