package com.example.eproject4.Service;

import com.example.eproject4.DTO.UserRegistrationDto;
import com.example.eproject4.Entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
