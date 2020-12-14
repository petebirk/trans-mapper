package org.openbank.model;

public class Transaction {
	String id;
	Account this_account;
	Account other_account;
	Details details;
	TransactionMetadata metadata;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Account getThis_account() {
		return this_account;
	}
	public void setThis_account(Account this_account) {
		this.this_account = this_account;
	}
	public Account getOther_account() {
		return other_account;
	}
	public void setOther_account(Account other_account) {
		this.other_account = other_account;
	}
	public Details getDetails() {
		return details;
	}
	public void setDetails(Details details) {
		this.details = details;
	}
	public TransactionMetadata getMetadata() {
		return metadata;
	}
	public void setMetadata(TransactionMetadata metadata) {
		this.metadata = metadata;
	}
	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", this_account=" + this_account + ", other_account=" + other_account
				+ ", details=" + details + ", metadata=" + metadata + "]";
	}
}
