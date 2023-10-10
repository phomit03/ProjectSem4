package com.example.eproject4.Repository;

import com.example.eproject4.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);

	boolean existsByUsername(String username);
}
