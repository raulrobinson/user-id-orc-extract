package co.com.telefonica.ws.controller;

import co.com.telefonica.ws.domain.OdsUser;
import co.com.telefonica.ws.dto.DetalleDocumentosDto;
import co.com.telefonica.ws.dto.DetalleDocumentosFsBase;
import co.com.telefonica.ws.service.UserService;
import co.com.telefonica.ws.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/oracle")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> WORKING 15 */
    @GetMapping("/registros/{loadDate}/{pageSize}/{pageNumber}")
    public ResponseEntity<List<DetalleDocumentosFsBase>> obtenerRegistrosPaginadosPorLoadDate(
            @PathVariable String loadDate,
            @PathVariable int pageSize,
            @PathVariable int pageNumber
    ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(loadDate);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        List<DetalleDocumentosFsBase> registers = service.getRegistersPaginadosPorLoadDate(parsedDate, pageSize, pageNumber);
        return ResponseEntity.ok(registers);
    }

    @GetMapping("/registros-odsuser/{loadDate}/{pageSize}/{pageNumber}")
    public ResponseEntity<?> obtenerRegistrosPaginadosPorLoadDateOdsUser(
            @PathVariable String loadDate,
            @PathVariable int pageSize,
            @PathVariable int pageNumber
    ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(loadDate);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        var registers = service.getRegistersPaginadosPorLoadDateOdsUser(parsedDate, pageSize, pageNumber);
        return ResponseEntity.ok(registers);
    }

    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> */

    // @GetMapping("/registros/{loadDate}")
    // public ResponseEntity<List<OdsUser>> obtenerRegistrosPorLoadDate(@PathVariable String loadDate) {
    //     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //     Date parsedDate;
    //     try {
    //         parsedDate = dateFormat.parse(loadDate);
    //     } catch (ParseException e) {
    //         return ResponseEntity.badRequest().build();
    //     }
    //     List<OdsUser> registros = service.obtenerRegistrosPorLoadDate(parsedDate);
    //     return ResponseEntity.ok(registros);
    // }
    //
    // @GetMapping("/registros-id/{loadDate}")
    // public ResponseEntity<List<OdsUser>> obtenerRegistrosPorLoadDateId(@PathVariable String loadDate) {
    //     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //     Date parsedDate;
    //     try {
    //         parsedDate = dateFormat.parse(loadDate);
    //     } catch (ParseException e) {
    //         return ResponseEntity.badRequest().build();
    //     }
    //     List<OdsUser> registros = service.obtenerRegistrosPorLoadDateId(parsedDate);
    //     return ResponseEntity.ok(registros);
    // }

    @GetMapping("/{loadDate}")
    public ResponseEntity<Object> pageLoadDate(@PathVariable String loadDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(loadDate);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        var require = service.sendDataToPgPerDatePage(parsedDate);
        if (require.getStatusCode().is4xxClientError()) {
            log.error(Constants.RESPONSE_CONTROLLER, require.getStatusCode(), loadDate);
            return new ResponseEntity<>(require.getStatusCode());
        } else if (require.getStatusCode().is2xxSuccessful()) {
          log.info(Constants.RESPONSE_CONTROLLER, require.getStatusCode(), loadDate);
          return new ResponseEntity<>(require.getStatusCode());
        }
        log.error(Constants.RESPONSE_CONTROLLER, require.getStatusCode(), loadDate);
        return new ResponseEntity<>(require.getStatusCode());
    }
}
