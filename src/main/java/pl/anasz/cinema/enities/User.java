package pl.anasz.cinema.enities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_name")
    @Length(min = 3, message = "Name should contains of min 3 characters")
    private String name;

    @Column(name = "user_surname")
    @Length(min = 3, message = "Surname should contains of min 3 characters")
    private String surname;

    @Column(name = "reservation")
    @OneToMany(mappedBy = "booker")
    private List<Reservation> reservations;
}
