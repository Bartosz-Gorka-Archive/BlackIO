package put.io.black.java.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application code to ensure run SpringApplication
 * @see SpringApplication
 */
@SpringBootApplication(scanBasePackages = {"put.io.black.java.rest"})
public class ScenarioApplication {

    /**
     * Run SpringApplication with our ScenarioApplication class
     * @param args Program arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ScenarioApplication.class, args);
    }
}
