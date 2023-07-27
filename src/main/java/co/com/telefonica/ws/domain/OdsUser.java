package co.com.telefonica.ws.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "DETALLE_DOCUMENTOS_FS_BASE", schema = "DWHODS")
@AllArgsConstructor
@NoArgsConstructor
public class OdsUser {
    @Id
    private int id;

    @Column(name = "ID_TYPE")
    private String idType;

    @Column(name = "ID_NUMBER")
    private String idNumber;

    @Column(name = "LOAD_DATE")
    private Date loadDate;
}
