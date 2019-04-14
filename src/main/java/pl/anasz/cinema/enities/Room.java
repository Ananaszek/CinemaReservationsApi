package pl.anasz.cinema.enities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Data
@Table(name = "rooms")
public class Room {

    @Column(name = "room_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "room_number")
    private long number;

    @ManyToOne
    @JoinColumn(name="cinema_id", nullable=false)
    private Cinema cinema;
}
