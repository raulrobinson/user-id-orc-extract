package co.com.telefonica.ws.service;

import co.com.telefonica.ws.domain.OdsUser;
import co.com.telefonica.ws.dto.DetalleDocumentosDto;
import co.com.telefonica.ws.dto.DetalleDocumentosFsBase;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface UserService {
    /*TEST*/
    List<DetalleDocumentosFsBase> obtenerRegistrosPaginadosPorLoadDate(Date loadDate, int pageSize, int pageNumber);

    /*TEST*/
    List<OdsUser> obtenerRegistrosPorLoadDate(Date loadDate);

    List<OdsUser> obtenerRegistrosPorLoadDateId(Date loadDate);

    ResponseEntity<String> sendDataToPgPerDatePage(Date loadDate);
}
