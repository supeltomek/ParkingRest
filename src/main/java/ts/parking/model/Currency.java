package ts.parking.model;

import java.math.BigDecimal;

public enum Currency {
	PLN(new BigDecimal("1.0"));
	
	private BigDecimal currency;
	
	Currency(BigDecimal currency){
		this.currency = currency;
	}
	
	public BigDecimal getAmountInCurrency(BigDecimal amount) {
		return amount.multiply(currency);
	}
}
