package com.hospital.Patient;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Repository interface for DB
@Repository
public interface PatientsRepository extends JpaRepository<Patient, Long>{
	List<Patient> findBynameAndDate(String name, String date);
	List<Patient> findBynameAndDateNot(String name,String date);
}
