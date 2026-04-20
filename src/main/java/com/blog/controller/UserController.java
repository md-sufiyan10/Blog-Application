package com.blog.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponseDto;
import com.blog.payload.UserDTO;
import com.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponseDto> registerUser(@Valid @RequestBody UserDTO userDTO) {
		log.info("User registered successfully {} ", userDTO.getEmail());
		UserDTO createUser=userService.createUser(userDTO);

		return new ResponseEntity<>(
				ApiResponseDto.success("User Registered successfully", createUser), HttpStatus.CREATED);
	}
	// Only ADMIN can create users
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<ApiResponseDto> createuser(@Valid	@RequestBody UserDTO userDTO) {
		log.info("User created successfully", userDTO.getEmail());
		UserDTO Createuser = userService.createUser(userDTO);
		return new ResponseEntity<>(
				ApiResponseDto.success("User created successfully", Createuser), HttpStatus.CREATED);
	}


	// getting user by id
   // ✅ ADMIN and USER can view (USER can only view their own data - you can add logic)
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto> getUserById(@PathVariable Long id) {
		log.info("User getting successfully By ID: {}", id);
		UserDTO user = userService.getUserById(id);
		return ResponseEntity.ok(ApiResponseDto.success("User Found successfully", user));
	}
	@GetMapping()
	public ResponseEntity<ApiResponseDto> getAllUser(){
		log.info("REST Request to get All Users ");
		List<UserDTO> allUser = userService.findAllUser();
		if(allUser==null || allUser.isEmpty()){
				return ResponseEntity.ok(ApiResponseDto.success("No Users Data Found in DB:", allUser));
		}
		return  ResponseEntity.ok(ApiResponseDto.success("Users Retrieved successfully", allUser));
	}

	// ✅ Only ADMIN can update users
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseDto>  updateUser( @PathVariable Long id, @Valid @RequestBody UserDTO userDTO){
		log.info("REST Request to update User with ID: {}", id);

		UserDTO updatedUser = userService.updateUser(id, userDTO);
		return  ResponseEntity.ok(ApiResponseDto.success("User Updated Successfully..",userDTO.getId()));
		
	}
	// ✅ Only ADMIN can delete users
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseDto> deleteByUserId(@PathVariable Long id){
		log.info("REST Request to delete User with ID: {}", id);
		userService.deleteUserById(id);
		return ResponseEntity.ok(ApiResponseDto.success("User Deleted Successfully with ID : " +id , null));
	}
	// deleteing all users

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete-all")
	public ResponseEntity<ApiResponseDto> deleteAllUsersAndResetId(){
		log.info("REST Request to delete All Users ");
		userService.deleteAllUsersAndResetId();
		return ResponseEntity.ok(ApiResponseDto.success("User Deleted Successfully.ID counter reset to 1"));
	}
}