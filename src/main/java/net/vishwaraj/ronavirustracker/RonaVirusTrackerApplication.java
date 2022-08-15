package net.vishwaraj.ronavirustracker;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "Corona Tracker API", version = "1.0", description = "Corona Daily cases"))
public class RonaVirusTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RonaVirusTrackerApplication.class, args);
	}

}
