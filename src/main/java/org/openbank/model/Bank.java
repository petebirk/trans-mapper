package org.openbank.model;

public class Bank {
	String national_identifier;
	String name;
	
	public String getNational_identifier() {
		return national_identifier;
	}
	public void setNational_identifier(String national_identifier) {
		this.national_identifier = national_identifier;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Bank [national_identifier=" + national_identifier + ", name=" + name + "]";
	}
	
}
