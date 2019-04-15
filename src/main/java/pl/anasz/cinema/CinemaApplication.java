package pl.anasz.cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.h2.tools.Server;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


@SpringBootApplication
public class CinemaApplication {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}

	@PostConstruct
	private void initDb() {
		try {
			initSQLStatements();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initSQLStatements() throws SQLException {
		initializeRooms();
		initializeMovies();
		initializeCinemas();
		initializeSeats();
		initializeScreenings();
	}

	private void initializeScreenings() {
		System.out.println(String.format("****** Creating table: %s, and Inserting test data ******", "Screenings"));

		String moviesSqlStatements[] = {
				"drop table screenings if exists",
				"create table screenings(screening_id serial,movie_id int, room_id int,screening_time timestamp)",
				"insert into screenings(movie_id, room_id,screening_time) values(1,1,'2019-04-03 12:00:00')",
				"insert into screenings(movie_id, room_id,screening_time) values(1,2,'2019-05-03 15:00:00')",
				"insert into screenings(movie_id, room_id,screening_time) values(2,3,'2019-05-13 15:30:00')",
				"insert into screenings(movie_id, room_id,screening_time) values(3,3,'2019-05-20 20:00:00')"
		};

		Arrays.asList(moviesSqlStatements).forEach(sql -> {
			System.out.println(sql);
			jdbcTemplate.execute(sql);
		});

		System.out.println(String.format("****** Fetching from table: %s ******", "Screenings"));
		jdbcTemplate.query("select screening_id,movie_id, room_id,screening_time from screenings",
				new RowMapper<Object>() {
					@Override
					public Object mapRow(ResultSet resultSet, int i) throws SQLException {
						System.out.println(String.format("screening_id:%s,movie_id:%s,room_id:%s,screening_time:%s",
								resultSet.getString("screening_id"),
								resultSet.getString("movie_id"),
								resultSet.getString("room_id"),
								resultSet.getString("screening_time")
						));
						return null;
					}
				});
	}

	private void initializeSeats() throws SQLException{
		System.out.println(String.format("****** Creating table: %s, and Inserting test data ******", "Seats"));

		String moviesSqlStatements[] = {
				"drop table seats if exists",
				"create table seats(seat_id serial,seat_signature varchar(255), room_id int)",
				"insert into seats(seat_signature,room_id) values('A1',1)",
				"insert into seats(seat_signature,room_id) values('A2',1)",
				"insert into seats(seat_signature,room_id) values('A3',1)",
				"insert into seats(seat_signature,room_id) values('A4',1)",
				"insert into seats(seat_signature,room_id) values('A5',1)",
				"insert into seats(seat_signature,room_id) values('B1',1)",
				"insert into seats(seat_signature,room_id) values('B2',1)",
				"insert into seats(seat_signature,room_id) values('B3',1)",
				"insert into seats(seat_signature,room_id) values('B4',1)",
				"insert into seats(seat_signature,room_id) values('B5',1)",
				"insert into seats(seat_signature,room_id) values('C1',1)",
				"insert into seats(seat_signature,room_id) values('C2',1)",
				"insert into seats(seat_signature,room_id) values('C3',1)",
				"insert into seats(seat_signature,room_id) values('C4',1)",
				"insert into seats(seat_signature,room_id) values('C5',1)",

				"insert into seats(seat_signature,room_id) values('A1',2)",
				"insert into seats(seat_signature,room_id) values('A2',2)",
				"insert into seats(seat_signature,room_id) values('A3',2)",
				"insert into seats(seat_signature,room_id) values('A4',2)",
				"insert into seats(seat_signature,room_id) values('A5',2)",
				"insert into seats(seat_signature,room_id) values('B1',2)",
				"insert into seats(seat_signature,room_id) values('B2',2)",
				"insert into seats(seat_signature,room_id) values('B3',2)",
				"insert into seats(seat_signature,room_id) values('B4',2)",
				"insert into seats(seat_signature,room_id) values('B5',2)",
				"insert into seats(seat_signature,room_id) values('C1',2)",
				"insert into seats(seat_signature,room_id) values('C2',2)",
				"insert into seats(seat_signature,room_id) values('C3',2)",
				"insert into seats(seat_signature,room_id) values('C4',2)",
				"insert into seats(seat_signature,room_id) values('C5',2)",

				"insert into seats(seat_signature,room_id) values('A1',3)",
				"insert into seats(seat_signature,room_id) values('A2',3)",
				"insert into seats(seat_signature,room_id) values('A3',3)",
				"insert into seats(seat_signature,room_id) values('A4',3)",
				"insert into seats(seat_signature,room_id) values('A5',3)",
				"insert into seats(seat_signature,room_id) values('B1',3)",
				"insert into seats(seat_signature,room_id) values('B2',3)",
				"insert into seats(seat_signature,room_id) values('B3',3)",
				"insert into seats(seat_signature,room_id) values('B4',3)",
				"insert into seats(seat_signature,room_id) values('B5',3)",
				"insert into seats(seat_signature,room_id) values('C1',3)",
				"insert into seats(seat_signature,room_id) values('C2',3)",
				"insert into seats(seat_signature,room_id) values('C3',3)",
				"insert into seats(seat_signature,room_id) values('C4',3)",
				"insert into seats(seat_signature,room_id) values('C5',3)",
		};

		Arrays.asList(moviesSqlStatements).forEach(sql -> {
			System.out.println(sql);
			jdbcTemplate.execute(sql);
		});

		System.out.println(String.format("****** Fetching from table: %s ******", "Seats"));
		jdbcTemplate.query("select seat_id,seat_signature,room_id from seats",
				new RowMapper<Object>() {
					@Override
					public Object mapRow(ResultSet resultSet, int i) throws SQLException {
						System.out.println(String.format("seat_id:%s,seat_signature:%s,room_id:%s",
								resultSet.getString("seat_id"),
								resultSet.getString("seat_signature"),
								resultSet.getString("room_id")
						));
						return null;
					}
				});
	}

	private void initializeCinemas() throws SQLException {
		System.out.println(String.format("****** Creating table: %s, and Inserting test data ******", "Cinemas"));

		String moviesSqlStatements[] = {
				"drop table cinemas if exists",
				"create table cinemas(cinema_id serial,cinema_name varchar(255))",
				"insert into cinemas(cinema_name) values('Helios')"
		};

		Arrays.asList(moviesSqlStatements).forEach(sql -> {
			System.out.println(sql);
			jdbcTemplate.execute(sql);
		});

		System.out.println(String.format("****** Fetching from table: %s ******", "Cinemas"));
		jdbcTemplate.query("select cinema_id,cinema_name from cinemas",
				new RowMapper<Object>() {
					@Override
					public Object mapRow(ResultSet resultSet, int i) throws SQLException {
						System.out.println(String.format("cinema_id:%s,cinema_name:%s",
								resultSet.getString("cinema_id"),
								resultSet.getString("cinema_name")));
						return null;
					}
				});
	}

	private void initializeMovies() throws SQLException {
		System.out.println(String.format("****** Creating table: %s, and Inserting test data ******", "Movies"));

		String moviesSqlStatements[] = {
				"drop table movies if exists",
				"create table movies(movie_id serial,movie_title varchar(255))",
				"insert into movies(movie_title) values('In your face')",
				"insert into movies(movie_title) values('Deadpool')",
				"insert into movies(movie_title) values('Captain Marvel')"
		};

		Arrays.asList(moviesSqlStatements).forEach(sql -> {
			System.out.println(sql);
			jdbcTemplate.execute(sql);
		});

		System.out.println(String.format("****** Fetching from table: %s ******", "Movies"));
		jdbcTemplate.query("select movie_id,movie_title from movies",
				new RowMapper<Object>() {
					@Override
					public Object mapRow(ResultSet resultSet, int i) throws SQLException {
						System.out.println(String.format("movie_id:%s,movie_title:%s",
								resultSet.getString("movie_id"),
								resultSet.getString("movie_title")
						));
						return null;
					}
				});
	}

	private void initializeRooms() throws SQLException {
		System.out.println(String.format("****** Creating table: %s, and Inserting test data ******", "rooms"));

		String cinemaSqlStatements[] = {
				"drop table rooms if exists",
				"create table rooms(room_id serial,room_number int,cinema_id int)",
				"insert into rooms(room_number,cinema_id) values(1,1)",
				"insert into rooms(room_number,cinema_id) values(2,1)",
				"insert into rooms(room_number,cinema_id) values(3,1)"
		};

		Arrays.asList(cinemaSqlStatements).forEach(sql -> {
			System.out.println(sql);
			jdbcTemplate.execute(sql);
		});

		System.out.println(String.format("****** Fetching from table: %s ******", "Rooms"));
		jdbcTemplate.query("select room_id,room_number,cinema_id from rooms",
				new RowMapper<Object>() {
					@Override
					public Object mapRow(ResultSet rs, int i) throws SQLException {
						System.out.println(String.format("room_id:%s,room_number:%s,cinema_id:%s",
								rs.getString("room_id"),
								rs.getString("room_number"),
								rs.getString("cinema_id")));
						return null;
					}
				});
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server inMemoryH2DatabaseServer() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
	}

}
