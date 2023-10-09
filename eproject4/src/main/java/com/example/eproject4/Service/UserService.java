package com.example.eproject4.Service;

import com.example.eproject4.DTO.UserRegistrationDto;
import com.example.eproject4.Entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);

	List<User> getAllUsers();
	User getUserById(Long id);

//	User createUser(User user);
	User updateUser(Long id, User user);
	void deleteUser(Long id);
}
