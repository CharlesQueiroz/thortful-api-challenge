package api.challenge.thortful;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.boot.SpringApplication.run;

@EnableRetry
@EnableScheduling
@SpringBootApplication
public class ThortfulApplication {

    public static void main(String[] args) {
        run(ThortfulApplication.class, args);
    }
}
