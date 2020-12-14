package org.openbank.model;

public class Balance {
	String currency;
	String amount;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "Balance [currency=" + currency + ", amount=" + amount + "]";
	}
	
	
}
