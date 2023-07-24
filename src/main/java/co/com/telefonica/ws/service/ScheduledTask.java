package co.com.telefonica.ws.service;

import co.com.telefonica.ws.persistence.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * The type Scheduled task.
 */
@Slf4j
@Service
public class ScheduledTask {

    private final JdbcTemplate jdbcTemplate;
    private final UsersRepository repository;

    /**
     * Instantiates a new Scheduled task.
     *
     * @param jdbcTemplate the jdbc template
     * @param repository   the repository
     */
    @Autowired
    public ScheduledTask(JdbcTemplate jdbcTemplate, UsersRepository repository) {
        this.jdbcTemplate = jdbcTemplate;
        this.repository = repository;
    }

    /**
     * The Batch counter.
     */
    int batchCounter = 0;

    /**
     * Execute procedure.
     *
     * @Scheduled(cron = "0 0 6 * * ?") Execute at 1:00 AM every day (-5 GMT).
     * @Scheduled(fixedDelayString = "${scheduled.delay}")
     * @Scheduled(cron = "${sec.min.hour.day.month}")
     * @void                            Only Console Output.
     */
    @Scheduled(cron = "0 0 6 * * *")
    public void executeProcedure() {

        // Set SP to CALL.
        String procedureCall = "CALL DWHODS.PRO_BI_XY_DOCUMENTOS_FS('')";
        jdbcTemplate.update(procedureCall);

        // Set DATE.
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDateTime.format(formatter);
        int totalProcess = repository.countUsersByDate(formattedDate);

        // Console output process.
        batchCounter++;
        log.info("BATCH = {} : PROCESSED = {} : DATE = {}", batchCounter, totalProcess, new Date());
    }

}

