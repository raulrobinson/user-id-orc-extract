package co.com.telefonica.ws.persistence.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * The type Users.
 */
@Data
@Entity
@Table(name = "USERS", schema = "DWHODS")
public class Users {

    @Column(name = "NAME")
    private String name;

    @Id
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "LOAD_DATE")
    private Date loadDate;

}
