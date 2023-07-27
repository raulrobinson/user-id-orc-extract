package co.com.telefonica.ws.controller;

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

@Slf4j
@RestController
@RequestMapping("/v1/oracle")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

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
