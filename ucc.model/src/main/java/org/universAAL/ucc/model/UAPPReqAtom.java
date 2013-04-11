package org.universAAL.ucc.model;

public class UAPPReqAtom {
	String name;
	String value;
	String criteria;
	
	public UAPPReqAtom() {
		
	}
	public UAPPReqAtom(String n, String v, String c) {
		this.name = n;
		this.value = v;
		this.criteria = c;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String v) {
		this.value = v;
	}
	
	public String getCriteria() {
		return criteria;
	}
	
	public void setCriteria(String c) {
		this.criteria = c;
	}
	
}
