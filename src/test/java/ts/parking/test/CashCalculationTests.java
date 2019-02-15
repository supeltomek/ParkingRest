package ts.parking.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.junit.Test;

import ts.parking.model.Currency;
import ts.parking.model.DriverType;
import ts.parking.service.CashCalculations;

public class CashCalculationTests {

	private LocalDateTime start = LocalDateTime.of(2019, 2, 8, 11, 30);
	private LocalDateTime end = LocalDateTime.of(2019, 2, 8, 14, 56);
	
	@Test
	public void parkingAmount_whenDriverDisabled_thenNextHourLarger1p2fromHourBefore() {
		assertEquals((BigDecimal.valueOf(0.0)).add(BigDecimal.valueOf(2.0)).add(BigDecimal.valueOf(2.4)).add(BigDecimal.valueOf(2.88))
				.setScale(2, RoundingMode.HALF_UP),
				CashCalculations.calculateParkingPaymentAmount(start, end, DriverType.Disabled, Currency.PLN));
	}
	
	@Test
	public void parkinAmount_whenDriverRegular_thenNextHourLarger1p2fromHourBefore() {
		assertEquals((BigDecimal.valueOf(1.0)).add(BigDecimal.valueOf(2.0)).add(BigDecimal.valueOf(3.0)).add(BigDecimal.valueOf(4.5))
				.setScale(2, RoundingMode.HALF_UP),
				CashCalculations.calculateParkingPaymentAmount(start, end, DriverType.Regular, Currency.PLN));
	}
}
