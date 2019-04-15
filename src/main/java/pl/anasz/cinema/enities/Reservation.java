package pl.anasz.cinema.enities;

import lombok.Data;
import pl.anasz.cinema.enities.constraints.BookerNameConstraint;
import pl.anasz.cinema.enities.constraints.BookerSurnameConstraint;
import pl.anasz.cinema.enities.constraints.SeatReservationConstraint;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "reservations")
@GroupSequence(value = {Reservation.NotNullInterface.class, Reservation.BookerNameInterface.class, Reservation.SeatReservationInterface.class, Reservation.BookerSurnameInterface.class, Reservation.class})
public class Reservation {

    @Column(name = "reservation_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "movie_title")
    private String movieTitle;

    @Column(name = "reservation_expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "number_of_tickets")
    private int numberOfTickets;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @BookerNameConstraint(message = "Name has to start with capital letter!", groups = BookerNameInterface.class)
    @BookerSurnameConstraint(message = "Surname has to start with capital letter!", groups = BookerSurnameInterface.class)
    private User booker;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @ManyToMany(mappedBy = "reservations")
    @NotNull(message = "You have to reserve at least 1 seat!", groups = NotNullInterface.class)
    @SeatReservationConstraint(message = "You have to select neighbouring seatsSignatures!", groups = SeatReservationInterface.class)
    private List<Seat> seats;

    interface NotNullInterface{}
    interface BookerNameInterface{}
    interface BookerSurnameInterface{}
    interface SeatReservationInterface{}
}
