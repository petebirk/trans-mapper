package org.openbank.model;

public class Details {
	String type;
	String description;
	String posted;
	String completed;
	Balance new_balance;
	Value value;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPosted() {
		return posted;
	}
	public void setPosted(String posted) {
		this.posted = posted;
	}
	public String getCompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
	public Balance getNew_balance() {
		return new_balance;
	}
	public void setNew_balance(Balance new_balance) {
		this.new_balance = new_balance;
	}
	public Value getValue() {
		return value;
	}
	public void setValue(Value value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Details [type=" + type + ", description=" + description + ", posted=" + posted + ", completed="
				+ completed + ", new_balance=" + new_balance + ", value=" + value + "]";
	}
}
