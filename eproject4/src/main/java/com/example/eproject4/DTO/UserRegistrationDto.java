package com.example.eproject4.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {
	private String username;
	private String address;
	private LocalDate dateOfBirth;
	private String fullName;
	private String phone;
	private String email;
	private String password;

}
