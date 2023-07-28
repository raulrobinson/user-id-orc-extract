package co.com.telefonica.ws.service;

import co.com.telefonica.ws.domain.OdsUser;
import co.com.telefonica.ws.dto.DetalleDocumentosFsBase;
import co.com.telefonica.ws.dto.PgUserDTO;
import co.com.telefonica.ws.repository.DetalleDocumentosFsBaseRepository;
import co.com.telefonica.ws.repository.OdsUserRepository;
import co.com.telefonica.ws.repository.UsersRepository;
import co.com.telefonica.ws.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Value("${ms.postgres.url}")
    private String msUrlPg;

    private final RestTemplate restTemplate;
    private final UsersRepository userRepository;
    private final OdsUserRepository odsUserRepository;
    private final DetalleDocumentosFsBaseRepository repository;

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate, UsersRepository userRepository, OdsUserRepository odsUserRepository, DetalleDocumentosFsBaseRepository repository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.odsUserRepository = odsUserRepository;
        this.repository = repository;
    }

    /* >>>>>>>>>>>>>>>>>>>>>>>>>> WORKING 15 */
    @Override
    public List<DetalleDocumentosFsBase> getRegistersPaginadosPorLoadDate(Date loadDate, int pageSize, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<DetalleDocumentosFsBase> page = repository.findByLoadDate(loadDate, pageRequest);
        return page.getContent();
    }

    @Override
    public ResponseEntity<String> getRegistersPaginadosPorLoadDateOdsUser(Date loadDate, int pageSize, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<OdsUser> page = odsUserRepository.findByLoadDate(loadDate, pageRequest);
        var userList =  convertBatchOdsToPgPage(page);
        var response = sendToPgThree(userList);
        return new ResponseEntity<>(response.getStatusCode());
    }

    /* >>>>>>>>>>>>>>>>>>>>>>>>>> */

    // @Override
    // public List<OdsUser> obtenerRegistrosPorLoadDate(Date loadDate) {
    //     int offset = 0;
    //     List<OdsUser> registrosTotales = new ArrayList<>();
    //     List<OdsUser> registros;
    //     do {
    //         registros = odsUserRepository.findInGroupsOf100ByLoadDate(loadDate, offset);
    //         registrosTotales.addAll(registros);
    //         offset += 100;
    //     } while (!registros.isEmpty());
    //
    //     return registrosTotales;
    // }
    //
    // @Override
    // public List<OdsUser> obtenerRegistrosPorLoadDateId(Date loadDate) {
    //     int offset = 0;
    //     List<OdsUser> registrosTotales = new ArrayList<>();
    //     List<OdsUser> registros;
    //     //PageRequest pageRequest = PageRequest.of(0, 10);
    //     do {
    //         registros = odsUserRepository.findInGroupsOf100ByLoadDateId(loadDate, offset);
    //         registrosTotales.addAll(registros);
    //         offset += 3;
    //     } while (!registros.isEmpty());
    //
    //     return registrosTotales;
    // }
    /**** */

    @Override
    public ResponseEntity<String> sendDataToPgPerDatePage(Date loadDate) {
        try {
            // Convert Date to String.
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(loadDate);

            // Get Total Pages by loadDate.
            var totalRegisters = userRepository.findUsersByLoadDateStr(dateString).size();

            // Construct the batch by load_date.
            int size = 10; // SIZE

            // Get total pages.
            var totalPages = totalRegisters / size;

            // FOR process register per page.
            HashMap<String, Integer> response = new HashMap<>();
            for (int i = 0; i < totalPages; i++) { // # PAGE
                PageRequest pageRequest = PageRequest.of(i, size);
                Page<OdsUser> usersPage = odsUserRepository.findByLoadDate(loadDate, pageRequest);
                var userList =  convertBatchOdsToPgPage(usersPage);
                response.put("PAGE: " + i, userList.size());
                sendToPg(userList, dateString, i);
            }
            if (response.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(String.valueOf(totalRegisters), HttpStatus.OK);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public List<PgUserDTO> convertBatchOdsToPgPage(Page<OdsUser> usersPage) {
        List<PgUserDTO> userList = new ArrayList<>();
        for (OdsUser pgItem : usersPage) {
            PgUserDTO pgUser = new PgUserDTO();
            pgUser.setIdType(pgItem.getIdType());
            pgUser.setIdNumber(pgItem.getIdNumber());
            userList.add(pgUser);
        }
        return userList;
    }

    public Object sendToPg(List<PgUserDTO> userList,
                           String loadDate,
                           int page) {
        try {
            String url = msUrlPg;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<PgUserDTO>> requestEntity = new HttpEntity<>(userList, headers);
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info(Constants.SUCCESS_SERVICE, userList.size(), page, loadDate);
                return new ResponseEntity<>("200 OK", HttpStatus.OK);
            }
        } catch (ResourceAccessException ex) {
            log.error(Constants.UNAVAILABLE_SERVICE, userList.size(), page, loadDate);
            return new ResponseEntity<>("500 Not Unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        }
        log.error(Constants.BAD_REQUEST_SERVICE, userList.size(), page, loadDate);
        return new ResponseEntity<>("400 Bad Request", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> sendToPgThree(List<PgUserDTO> userList) {
        try {
            String url = msUrlPg;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<PgUserDTO>> requestEntity = new HttpEntity<>(userList, headers);
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info(Constants.RESPONSE_TOTAL, userList.size());
                return new ResponseEntity<>("200 OK", HttpStatus.OK);
            }
        } catch (ResourceAccessException ex) {
            log.error(Constants.RESPONSE_TOTAL, userList.size());
            return new ResponseEntity<>("500 Not Unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        }
        log.error(Constants.RESPONSE_TOTAL, userList.size());
        return new ResponseEntity<>("400 Bad Request", HttpStatus.BAD_REQUEST);
    }
}
