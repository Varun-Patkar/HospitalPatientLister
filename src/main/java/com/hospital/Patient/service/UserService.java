package com.hospital.Patient.service;

import com.hospital.Patient.Login.User;

public interface UserService {
	public void saveUser(User user);
	public boolean isUserAlreadyPresent(User user);
	public User findUserByEmail(String name);
	public User findUserByName(String name);
}
