package com.hospital.Patient;

//Query Class
public class Query {
	public String name;
	public String date;
	public Query() {
		
	}
	public Query(String name,String date) {
		this.name=name;
		this.date=date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
