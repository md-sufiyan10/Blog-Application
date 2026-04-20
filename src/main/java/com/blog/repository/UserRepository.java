package com.blog.repository;

import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blog.entity.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	// Find user by email
	
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);

	// Check if email already exists
    @Modifying
	@Transactional
	@Query(value="delete from users", nativeQuery = true)
	void deleteAllUsers();

	@Modifying
     @Transactional
	@Query(value = "ALTER table users AUTO_INCREMENT=1",nativeQuery = true)
	void resetAutoIncrement();
}
