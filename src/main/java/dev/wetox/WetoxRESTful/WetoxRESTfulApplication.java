package dev.wetox.WetoxRESTful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WetoxRESTfulApplication {

	public static void main(String[] args) {
		SpringApplication.run(WetoxRESTfulApplication.class, args);
	}

}
