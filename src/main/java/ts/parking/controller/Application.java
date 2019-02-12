package ts.parking.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ts.parking.model.ParkingDAO;

@SpringBootApplication
public class Application {

	@Bean
	protected ParkingDAO repository() {
		return new ParkingDAO();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}