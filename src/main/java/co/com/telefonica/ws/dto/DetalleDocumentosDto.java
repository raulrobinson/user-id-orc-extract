package co.com.telefonica.ws.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DetalleDocumentosDto {
    @Id
    private int id;

    @Column(name = "ID_TYPE")
    private String idType;

    @Column(name = "ID_NUMBER")
    private String idNumber;

    @Column(name = "LOAD_DATE")
    private Date loadDate;
}
