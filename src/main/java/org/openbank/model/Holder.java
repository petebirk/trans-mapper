package org.openbank.model;

public class Holder {
	String name;
	Boolean is_alias;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIs_alias() {
		return is_alias;
	}
	public void setIs_alias(Boolean is_alias) {
		this.is_alias = is_alias;
	}
	
	@Override
	public String toString() {
		return "Holder [name=" + name + ", is_alias=" + is_alias + "]";
	}
	
	
}
