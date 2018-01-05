package put.io.black.java.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"put.io.black.java.rest"})
public class ScenarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScenarioApplication.class, args);
    }
}
