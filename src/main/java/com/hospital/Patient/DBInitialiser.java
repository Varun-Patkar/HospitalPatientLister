package com.hospital.Patient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//Initialiser class
@Component
public class DBInitialiser implements CommandLineRunner{
	private PatientsRepository patientsRepository;
	
	//Initialise DB on startup
	@Autowired
	public DBInitialiser(PatientsRepository patientsRepository) {
		// TODO Auto-generated constructor stub
		this.patientsRepository=patientsRepository;
	}
	
	//Adding Default values to local DB
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
	}

}
