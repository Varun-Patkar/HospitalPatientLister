package com.hospital.Patient.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.Patient.Login.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	public Role findByRole(String role);

}
