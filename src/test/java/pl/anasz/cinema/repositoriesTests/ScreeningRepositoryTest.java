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
import pl.anasz.cinema.enities.Movie;
import pl.anasz.cinema.enities.Screening;
import pl.anasz.cinema.repositories.ScreeningRepository;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ScreeningRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ScreeningRepository screeningRepository;

    public ScreeningRepositoryTest() {
    }

    private Movie movie = new Movie();
    private Screening screening = new Screening();

    @Before
    public void createTestEntities(){
        movie.setTitle("Chilling adventures of Sabrina");
        entityManager.persist(movie);

        screening.setRoomId(3);
        screening.setScreeningTime(LocalDateTime.of(2019,04,12,12,00,00));
        screening.setMovie(movie);
        entityManager.persist(screening);
    }

    @After
    public void removeTestEntities(){
        entityManager.remove(movie);
        entityManager.remove(screening);
    }

    @Test
    public void whenFindRoomIdForScreening_thenReturnRoom(){
        //when
        long foundRoomId = screeningRepository.findRoomIdForScreening(screening.getMovie().getTitle(),screening.getScreeningTime());

        //then
        Assert.assertEquals(foundRoomId,screening.getRoomId());
    }

    @Test
    public void whenFindMoviesInInterval_thenReturnMovies(){
        //given
        Object[] movieObject =  {movie.getTitle(),screening.getScreeningTime()};

        //when
        List<Object[]> foundMovie = screeningRepository.findMoviesInInterval(screening.getScreeningTime(),LocalDateTime.now().plusDays(2));

        //then
        Assert.assertEquals(foundMovie.get(foundMovie.size()-1),movieObject);
    }

    @Test
    public void whenFindRoomIdByScreeningId_thenReturnRoomId(){
        //when
        long foundRoomId = screeningRepository.findRoomIdByScreeningId(screening.getId());

        //then
        Assert.assertEquals(foundRoomId,screening.getRoomId());
    }
}
