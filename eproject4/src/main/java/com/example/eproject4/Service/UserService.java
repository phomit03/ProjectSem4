package com.example.eproject4.Service;

import com.example.eproject4.DTO.UserRegistrationDto;
import com.example.eproject4.Entity.Role;
import com.example.eproject4.Entity.User;
import com.example.eproject4.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
	private UserRepository userRepository;

	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public User save(UserRegistrationDto registrationDto) {
		User user = new User(
				registrationDto.getUsername(),
				registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()),
				registrationDto.getAddress(),
				registrationDto.getDateOfBirth(),
				registrationDto.getFullName(),
				registrationDto.getPhone(),
				Arrays.asList(new Role("ROLE_USER"))
		);
		return userRepository.save(user);
	}
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}


	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
	}

//	@Override
//	public User createUser(User user) {
//		return userRepository.save(user);
//	}

	public User updateUser(Long id, User user) {
		User existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
		existingUser.setUsername(user.getUsername());
		existingUser.setEmail(user.getEmail());
		existingUser.setAddress(user.getAddress());
		existingUser.setFullName(user.getFullName());
		existingUser.setPhone(user.getPhone());
		existingUser.setDateOfBirth(user.getDateOfBirth());
		if (!user.getPassword().equals(existingUser.getPassword())) {
			existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		return userRepository.save(existingUser);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
