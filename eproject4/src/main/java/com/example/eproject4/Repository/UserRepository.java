package com.example.eproject4.Repository;

import com.example.eproject4.Entity.New;
import com.example.eproject4.Entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);

	boolean existsByUsername(String username);

	@Query(value = "SELECT * FROM `user` WHERE (:username is null or username like CONCAT('%',:username,'%')) "+
			" AND (:status is null or status like CONCAT('%',:status,'%')) " +
			" AND (:phone is null or phone like CONCAT('%',:phone,'%')) " +
			" AND (:email is null or email like  CONCAT('%',:email,'%'))", nativeQuery = true)
	List<User> searchUsers(@Param("username") String username, @Param("status") Integer status,
						  @Param("phone") String phone,@Param("email") String email, Pageable pageable);

	@Query(value = "SELECT * FROM `user` WHERE (:username is null or username like CONCAT('%',:username,'%')) "+
			" AND (:status is null or status like CONCAT('%',:status,'%')) " +
			" AND (:phone is null or phone like CONCAT('%',:phone,'%')) " +
			" AND (:email is null or email like  CONCAT('%',:email,'%'))", nativeQuery = true)
	List<User> searchUsers1(@Param("username") String username, @Param("status") Integer status,
						  @Param("phone") String phone,@Param("email") String email);

}
