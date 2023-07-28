package co.com.telefonica.ws.service;

import co.com.telefonica.ws.domain.OdsUser;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface UserService {
    /*TEST*/
    List<OdsUser> obtenerRegistrosPorLoadDate(Date loadDate);

    ResponseEntity<String> sendDataToPgPerDatePage(Date loadDate);
}
