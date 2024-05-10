package de.fatihkesikli.contargodemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class ContargodemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContargodemoApplication.class, args);
	}
}
