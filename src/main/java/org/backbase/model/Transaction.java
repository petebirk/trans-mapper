package org.backbase.model;

public class Transaction {
	String id;
	String accountId;
	String counterpartyAccount;
	String counterpartyName;
	String counterPartyLogoPath;
	String instructedAmount;
	String instructedCurrency;
	String transactionAmount;
	String transactionCurrency;
	String transactionType;
	String description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getCounterpartyAccount() {
		return counterpartyAccount;
	}
	public void setCounterpartyAccount(String counterpartyAccount) {
		this.counterpartyAccount = counterpartyAccount;
	}
	public String getCounterpartyName() {
		return counterpartyName;
	}
	public void setCounterpartyName(String counterpartyName) {
		this.counterpartyName = counterpartyName;
	}
	public String getCounterPartyLogoPath() {
		return counterPartyLogoPath;
	}
	public void setCounterPartyLogoPath(String counterPartyLogoPath) {
		this.counterPartyLogoPath = counterPartyLogoPath;
	}
	public String getInstructedAmount() {
		return instructedAmount;
	}
	public void setInstructedAmount(String instructedAmount) {
		this.instructedAmount = instructedAmount;
	}
	public String getInstructedCurrency() {
		return instructedCurrency;
	}
	public void setInstructedCurrency(String instructedCurrency) {
		this.instructedCurrency = instructedCurrency;
	}
	public String getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getTransactionCurrency() {
		return transactionCurrency;
	}
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", accountId=" + accountId + ", counterpartyAccount=" + counterpartyAccount
				+ ", counterpartyName=" + counterpartyName + ", counterPartyLogoPath=" + counterPartyLogoPath
				+ ", instructedAmount=" + instructedAmount + ", instructedCurrency=" + instructedCurrency
				+ ", transactionAmount=" + transactionAmount + ", transactionCurrency=" + transactionCurrency
				+ ", transactionType=" + transactionType + ", description=" + description + "]";
	}
	
	
}
