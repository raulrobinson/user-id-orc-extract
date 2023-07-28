package co.com.telefonica.ws.dto;

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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DETALLE_DOCUMENTOS_FS_BASE", schema = "DWHODS")
public class DetalleDocumentosFsBase {

    @Id
    @Column(name = "ID_NUMBER")
    private String idNumber;

    @Column(name = "ID_TYPE")
    private String idType;

    @Column(name = "LOAD_DATE")
    private Date loadDate;

}

