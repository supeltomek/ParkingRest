package ts.parking.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import ts.parking.model.Currency;
import ts.parking.model.DriverType;
import ts.parking.model.Parking;

public class CashCalculations {

	public static BigDecimal calculateParkingPaymentAmount(LocalDateTime start, LocalDateTime end, DriverType driverType, Currency currency) {
		BigDecimal paymentAmount = BigDecimal.ZERO;
		int parkingTimeInMinutes = Math.toIntExact(ChronoUnit.MINUTES.between(start, end));
		int parkingTimeEachStartedHour = 0;
		if(parkingTimeInMinutes % 60 > 0) {
			parkingTimeEachStartedHour = parkingTimeInMinutes / 60 + 1;
		}
		else if(parkingTimeInMinutes % 60 < 0) {
			parkingTimeEachStartedHour = 1;
		}
		else {
			parkingTimeEachStartedHour = parkingTimeInMinutes / 60;
		}
		
		if(parkingTimeEachStartedHour <= 1) {
			if(driverType == DriverType.Regular) {
				paymentAmount = currency.getAmountInCurrency(BigDecimal.valueOf(1.0));
			}
			else {
				paymentAmount = BigDecimal.ZERO;
			}
		}
		else if(parkingTimeEachStartedHour <= 2) {
			if(driverType == DriverType.Regular) {
				paymentAmount = currency.getAmountInCurrency(BigDecimal.valueOf(3.0));
			}
			else {
				paymentAmount = currency.getAmountInCurrency(BigDecimal.valueOf(2.0));
			}
		}
		else if(parkingTimeEachStartedHour > 2) {
			BigDecimal calculatedAmount = BigDecimal.ZERO;
			BigDecimal ratioDisable = new BigDecimal("1.2");
			BigDecimal ratioRegular = new BigDecimal("1.5");
			BigDecimal powerDisable = ratioDisable.pow(parkingTimeEachStartedHour-1);
			BigDecimal powerRegular = ratioRegular.pow(parkingTimeEachStartedHour-1);
			if(driverType == DriverType.Regular) {
				calculatedAmount = BigDecimal.valueOf(2.0)
						.multiply((BigDecimal.valueOf(1.0).subtract(powerRegular))
								.divide(BigDecimal.valueOf(1.0).subtract(ratioRegular)));
				paymentAmount = currency.getAmountInCurrency(BigDecimal.valueOf(1.0).add(calculatedAmount));
			}
			else {
				calculatedAmount = BigDecimal.valueOf(2.0)
						.multiply((BigDecimal.valueOf(1.0).subtract(powerDisable))
								.divide(BigDecimal.valueOf(1.0).subtract(ratioDisable)));
				paymentAmount = calculatedAmount;
			}
		}
		return paymentAmount.setScale(2, RoundingMode.HALF_EVEN);		
	}
	
	public static BigDecimal calculateAmountFromDate(int year, int month, int day, Iterable<Parking> parkings) {
		BigDecimal amountFromDate = BigDecimal.ZERO;
		for(Parking p : parkings) {
			if(p.getStartDateTime().getYear() == year 
					&& p.getStartDateTime().getMonthValue() == month 
					&& p.getStartDateTime().getDayOfMonth() == day) {
				amountFromDate = amountFromDate.add(p.getPayment().getPaymentAmount());
			}
		}
		return amountFromDate.setScale(2, RoundingMode.HALF_EVEN);
	}

}
