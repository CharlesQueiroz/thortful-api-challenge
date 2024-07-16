package api.challenge.thortful;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

import static org.springframework.boot.SpringApplication.run;

@EnableRetry
@SpringBootApplication
public class ThortfulApplication {

    public static void main(String[] args) {
        run(ThortfulApplication.class, args);
    }
}
