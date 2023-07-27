package co.com.telefonica.ws.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Users {
    @Id
    private int id;
    private String idType;
    private String idNumber;
    private Date loadDate;
}
