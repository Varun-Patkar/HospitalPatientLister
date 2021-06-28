package com.hospital.Patient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Patient Class
@Entity
public class Patient {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	public long id;
	
	public String name;
	public int age;
	public String date;
	public int temperature;
	public String symptoms;
	public Patient(){
		
	}
	public Patient(String name,int age,String date,int temperature,String symptoms){
		this.name=name;
		this.age=age;
		this.date=date;
		this.temperature=temperature;
		this.symptoms=symptoms;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public String getSymptoms() {
		return symptoms;
	}
	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}
	
}
