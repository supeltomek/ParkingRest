package ts.parking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import ts.parking.service.CashCalculations;

public class ParkingDAO {

	private final Map<Integer, Parking> parkingSpots = Collections.synchronizedMap(new HashMap<Integer, Parking>());
	
	private final AtomicInteger lastid = new AtomicInteger(0);

	public Collection<Parking> listAllActive() {
		return parkingSpots.values();
	}
	
	public Parking getByVehiclePlateNumber(String vehiclePlateNumber) {
		for(Entry<Integer, Parking> parking : parkingSpots.entrySet()) {
			if(parking.getValue().getVehiclePlateNumber().equals(vehiclePlateNumber)) {
				return parking.getValue();
			}
		}
		return null;
	}

	public Parking startParking(String vehiclePlateNumber, String startDateTime, String endDateTime, String driverType) {
		int id = lastid.getAndIncrement();
		LocalDateTime start = LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
		LocalDateTime end = LocalDateTime.parse(endDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
		DriverType dType;
		if(driverType == DriverType.Regular.name()) {
			dType = DriverType.Regular;
		}
		else {
			dType = DriverType.Disabled;
		}
		Parking parking = new Parking(vehiclePlateNumber, start, end, dType);
		parkingSpots.put(id, parking);
		return parkingSpots.get(id);
	}

	public BigDecimal getParkingAmount(String vehiclePlateNumber) {
		for(Entry<Integer, Parking> parking : parkingSpots.entrySet()) {
			if(parking.getValue().getVehiclePlateNumber().equals(vehiclePlateNumber)) {
				return parking.getValue().getPayment().getPaymentAmount();
			}
		}
		return null;
	}

	public BigDecimal getAmountFromDate(int year, int month, int day) {
		return CashCalculations.calculateAmountFromDate(year, month, day, parkingSpots.values());
	}
	
	public void clearData() {
		parkingSpots.clear();
	}

}
