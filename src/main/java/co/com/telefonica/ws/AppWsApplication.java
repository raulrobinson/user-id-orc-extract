package co.com.telefonica.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The type App ws application.
 *
 * @EnableScheduling Enable Schedule for a microservice process.
 */
@EnableScheduling
@SpringBootApplication
public class AppWsApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AppWsApplication.class, args);
    }

}
