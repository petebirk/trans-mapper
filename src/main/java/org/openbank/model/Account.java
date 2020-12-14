package org.openbank.model;

import java.util.List;

public class Account {
	String id;
	List<Holder> holders;
	Holder holder;
	String number;
	String kind;
	String IBAN;
	String swift_bic;
	Bank bank;
	AccountMetadata metadata;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Holder> getHolders() {
		return holders;
	}
	public void setHolders(List<Holder> holders) {
		this.holders = holders;
	}
	public Holder getHolder() {
		return holder;
	}
	public void setHolder(Holder holder) {
		this.holder = holder;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}
	public String getSwift_bic() {
		return swift_bic;
	}
	public void setSwift_bic(String swift_bic) {
		this.swift_bic = swift_bic;
	}
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	public AccountMetadata getMetadata() {
		return metadata;
	}
	public void setMetadata(AccountMetadata metadata) {
		this.metadata = metadata;
	}
	
	@Override
	public String toString() {
		return "Account [id=" + id + ", holders=" + holders + ", holder=" + holder + ", number=" + number + ", kind="
				+ kind + ", IBAN=" + IBAN + ", swift_bic=" + swift_bic + ", bank=" + bank + ", metadata=" + metadata
				+ "]";
	}
}
