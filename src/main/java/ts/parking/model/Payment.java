package ts.parking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import ts.parking.service.CashCalculations;

public class Payment {
	
	private LocalDateTime paymentDateTime;
	private BigDecimal paymentAmount;
	private Currency currency;
	
	public Payment(LocalDateTime startPaymentTime, LocalDateTime endPaymentTime, DriverType driverType, String currencyProperty) {
		setPaymentDateTime(startPaymentTime);
		setCurrency(currencyProperty);
		if(getCurrency()!=null) {
			setPaymentAmount(startPaymentTime, endPaymentTime, driverType, getCurrency());
		}
	}
	
	public LocalDateTime getPaymentDateTime() {
		return paymentDateTime;
	}
	
	public void setPaymentDateTime(LocalDateTime paymentDateTime) {
		this.paymentDateTime = paymentDateTime;
	}
	
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	
	public void setPaymentAmount(LocalDateTime start, LocalDateTime end, DriverType driverType, Currency currency) {
		this.paymentAmount = CashCalculations.calculateParkingPaymentAmount(start, end, driverType, currency);
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(String currencyProperty) {
		for(Currency c : Currency.values()) {
			if(c.name() == currencyProperty) {
				this.currency = c;
			}
		}
	}
}
