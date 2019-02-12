package ts.parking.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Parking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String vehiclePlateNumber;
	private LocalDateTime start;
	private LocalDateTime end;
	private DriverType driverType;
	private Payment payment;
	
	public Parking() {    	
	}
	
	public Parking(String vehiclePlateNumber, LocalDateTime start, LocalDateTime end, DriverType driverType) {
		this.vehiclePlateNumber = vehiclePlateNumber;
		this.start = start;
		this.end = end;
		this.driverType = driverType;
		setPayment(start, end, driverType);
	}

	public String getVehiclePlateNumber() {
		return vehiclePlateNumber;
	}
	
	public void setVehiclePlateNumber(String vehiclePlateNumber) {
		this.vehiclePlateNumber = vehiclePlateNumber;
	}
	
	public LocalDateTime getStartDateTime() {
		return start;
	}
	
	public void setStartDateTime(LocalDateTime start) {
		this.start = start;
	}
	
	public LocalDateTime getEndDateTime() {
		return end;
	}
	
	public void setEndDateTime(LocalDateTime end) {
		this.end = end;
	}
	
	public DriverType getDriverType() {
		return driverType;
	}
	
	public void setDriverType(DriverType driverType) {
		this.driverType = driverType;
	}
	
	public Payment getPayment() {
		return payment;
	}
	
	public void setPayment(LocalDateTime start, LocalDateTime end, DriverType driverType) {
		this.payment = new Payment(start, end, driverType, "PLN");
	}
}
