package co.com.telefonica.ws.service;

import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface UserService {
    ResponseEntity<String> sendDataToPgPerDatePage(Date loadDate);
}
