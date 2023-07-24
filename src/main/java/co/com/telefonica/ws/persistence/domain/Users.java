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
@Table(name = "DETALLE_DOCUMENTOS_FS_BASE", schema = "DWHODS")
public class Users {

    @Id
    @Column(name = "ID_NUMBER")
    private String idNumber;

    @Column(name = "ID_TYPE")
    private String idType;

    @Column(name = "LOAD_DATE")
    private Date loadDate;

}
