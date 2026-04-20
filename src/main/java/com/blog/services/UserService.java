package com.blog.services;

import java.util.List;

import com.blog.payload.UserDTO;



public interface UserService {

	UserDTO createUser(UserDTO userDTO);

	UserDTO getUserById(Long id);

	List<UserDTO> findAllUser();

	UserDTO updateUser(Long id, UserDTO userDTO);

	void deleteUserById(Long id);

	UserDTO getUserByEmail(String email);

	void deleteAllUsersAndResetId();
}
