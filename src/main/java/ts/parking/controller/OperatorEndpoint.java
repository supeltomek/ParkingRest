package ts.parking.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ts.parking.model.Parking;
import ts.parking.model.ParkingDAO;

@RestController 
@RequestMapping("/admin")
public class OperatorEndpoint {
	
	private final ParkingDAO repository;
	
	public OperatorEndpoint(ParkingDAO repository) {
		this.repository = repository;
	}
	
	@GetMapping
	public Iterable<Parking> getParkingList() {
		return repository.listAllActive();
	}

	@GetMapping("/{vehiclePlateNumber}")
	public Parking checkParkingActive(@PathVariable String vehiclePlateNumber) {
		Parking parking = repository.getByVehiclePlateNumber(vehiclePlateNumber);	
		if(parking != null) {
			return parking;
		}
		return null;
	}
	
	@GetMapping("/{year}/{month}/{day}")
	public BigDecimal amountReportFromDate(@PathVariable int year, @PathVariable int month, @PathVariable int day) {
		return repository.getAmountFromDate(year, month, day);
	}

}
