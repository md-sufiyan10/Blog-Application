package com.blog.services.impl;

import java.util.List;

import com.blog.exception.DuplicateResourceException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.entity.User;
import com.blog.exception.ResourceNotFoundException;
import com.blog.mapper.UserMapper;
import com.blog.payload.UserDTO;
import com.blog.repository.UserRepository;
import com.blog.services.UserService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating new user with email: {}", userDTO.getEmail());

        //1 Check if email already exists
        if (userRepository.existsByEmail(userDTO.getEmail())) {
			log.error(" Email Already exists {}", userDTO.getEmail());
            throw new DuplicateResourceException( "User " , "email" ,userDTO.getEmail());
        }

        // 2 Convert DTO to Entity
        User user = userMapper.toEntity(userDTO);

		// 3. ENCODE PASSWORD - CRITICAL for security!
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
         log.info("Password encoded Successfully : {}", userDTO.getPassword());

        // 4 Save to database
        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());

        // 5 Convert back to DTO and return (password will be null)
        return userMapper.toDto(savedUser);
    }

	@Override
	public UserDTO getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);

        // 1. Find user by ID, if not found throw exception
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException( "User " , " id ", id));

        // 2. Convert Entity to DTO and return
        return userMapper.toDto(user);
    }
	@Override
	public List<UserDTO> findAllUser() {
        log.info("Fetching all users");
		List<User> users = userRepository.findAll();
		return users.stream().map(userMapper::toDto).toList();
		
	}
	
// updating user
	@Override
	public UserDTO updateUser(Long id, UserDTO userDTO) {
		log.info("Updating user with ID: {}", id);

		// 1. Check if user exists
		User exixtsUser = userRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException(" User", " id ", id));

		// 2. Check if email is being changed
		boolean isEmailChanged =!exixtsUser.getEmail().equals(userDTO.getEmail());

		// 3. Only check duplicate if email is actually changed
		if(isEmailChanged && userRepository.existsByEmail(userDTO.getEmail())) {
			log.error(" Email Already exists {}", userDTO.getEmail());
			throw new DuplicateResourceException( "User " , "email ", userDTO.getEmail());
		}
		// 4 update entity with new values
		
		userMapper.updateEntityFromDto(userDTO, exixtsUser);

		// 4. If password is provided, encode it
		if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
			String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
			exixtsUser.setPassword(encodedPassword);
			log.info("Password updated and encoded for user ID: {}", id);
		}

		// 5 saved Updated user
		User updatedSave = userRepository.save(exixtsUser);
		log.info("User updated successfully with ID: {}", id);
		return userMapper.toDto(updatedSave);
	}

	// deleting by id
	@Override
	public void deleteUserById(Long id) {
        log.info("Deleting user with ID: {}", id);

		User user = userRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException(" User", " id ", id));
        userRepository.delete(user);
        log.info("User deleted successfully with ID: {}", id);
	}

	@Override
	public UserDTO getUserByEmail(String email) {
        log.info("Fetching user with email: {}", email);
        
		User user = userRepository.findByEmail(	email)
				.orElseThrow(()->new ResourceNotFoundException("User ", "email " , email));
		 return userMapper.toDto(user);
	}

	@Override
	public void deleteAllUsersAndResetId() {
		log.info("Deleting All Users and Reset Id Counter:");
		userRepository.deleteAllUsers();
		userRepository.resetAutoIncrement();
		log.info("All users deleted and ID counter reset successfully");
	}
}
