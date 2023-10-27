package com.example.eproject4.Service;

import com.example.eproject4.DTO.UserRegistrationDto;
import com.example.eproject4.Entity.Role;
import com.example.eproject4.Entity.User;
import com.example.eproject4.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
	// check username
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public User save(UserRegistrationDto registrationDto) {
		User user = new User();

		user.setUsername(registrationDto.getUsername());
		user.setEmail(registrationDto.getEmail());
		user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
		user.setAddress(registrationDto.getAddress());
		user.setDateOfBirth(registrationDto.getDateOfBirth());
		user.setFullName(registrationDto.getFullName());
		user.setPhone(registrationDto.getPhone());
		user.setStatus(1);
		user.setRoles(Arrays.asList(new Role("ROLE_USER")));

		return userRepository.save(user);
	}
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
	}

	public User updateUser(Long id, User user) {
		User existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
		existingUser.setEmail(user.getEmail());
		existingUser.setUsername(user.getUsername());
		existingUser.setFullName(user.getFullName());
		existingUser.setAddress(user.getAddress());
		existingUser.setPhone(user.getPhone());
		existingUser.setDateOfBirth(user.getDateOfBirth());
		existingUser.setStatus(user.getStatus());
		existingUser.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

		if (user.getPassword() != null && !user.getPassword().isEmpty() && !user.getPassword().equals(existingUser.getPassword())) {
			existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		// info check
		if (user.getUsername() != null && !user.getUsername().isEmpty()) {
			existingUser.setUsername(user.getUsername());
		}

		return userRepository.save(existingUser);
	}

	public void softDelete(Long id) {
		Optional<User> optionalEntity = userRepository.findById(id);
		if (optionalEntity.isPresent()) {
			User user = optionalEntity.get();
			user.setStatus(0);
			userRepository.save(user);
		} else {
			throw new EntityNotFoundException("Entity with id " + id + " not found.");
		}
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

	// phan trang
	public Page<User> findPaginated(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.userRepository.findAll(pageable);
	}

}
