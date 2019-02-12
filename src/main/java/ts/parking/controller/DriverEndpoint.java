package ts.parking.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ts.parking.model.Parking;
import ts.parking.model.ParkingDAO;

@RestController
@RequestMapping("/park")
public class DriverEndpoint {

	private final ParkingDAO repository;
	
	public DriverEndpoint(ParkingDAO repository) {
		this.repository = repository;
	}
	
	@PostMapping
	public Parking parkVehicle(@RequestParam("vehiclePlateNumber") String vehiclePlateNumber, @RequestParam("startDateTime") String startDateTime,
			@RequestParam("endDateTime") String endDateTime, @RequestParam("driverType") String driverType) {
		return repository.startParking(vehiclePlateNumber, startDateTime, endDateTime, driverType);
	}
	
	@GetMapping("/{vehiclePlateNumber}")
	public BigDecimal checkParkingAmount(@PathVariable String vehiclePlateNumber) {
		Parking parking = repository.getByVehiclePlateNumber(vehiclePlateNumber);	
		if(parking != null) {
			return parking.getPayment().getPaymentAmount();
		}
		return null;
	}

}
