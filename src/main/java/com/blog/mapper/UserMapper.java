package com.blog.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.blog.entity.User;
import com.blog.payload.UserDTO;


@Component
public class UserMapper {
	private final ModelMapper modelMapper;

	public UserMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	// Convert DTO to Entity
	
	public User toEntity(UserDTO userdto) {
		if(userdto==null) return null;

		User user=modelMapper.map(userdto, User.class);
//		user.setId(userdto.getId());
//		user.setName(userdto.getName());
//		user.setEmail(userdto.getEmail());
//		user.setPassword(userdto.getPassword()); // Will be encoded in service
//		user.setAbout(userdto.getAbout());
		return user;

	}
	
	// Convert entity to DTO
	
	public UserDTO toDto(User user) {
		if (user==null) {
			return null;
		}
		UserDTO userDTO=modelMapper.map(user, UserDTO.class);
//		userDTO.setId(user.getId());
//		userDTO.setName(user.getName());
//		userDTO.setEmail(user.getEmail());
//		userDTO.setAbout(user.getAbout());
		userDTO.setPassword(null); // NEVER send password to client!
		return userDTO;
	}
	
	// Update existing entity from DTO
	
	public void updateEntityFromDto(UserDTO userDTO, User user) {
		if(userDTO.getName()!=null)
			user.setName(userDTO.getName());
		
		if(userDTO.getEmail()!=null)
			user.setEmail(userDTO.getEmail());
		if(userDTO.getPassword()!=null && !userDTO.getPassword().isEmpty())
			user.setPassword(userDTO.getPassword());
		if(userDTO.getAbout()!=null)
			user.setAbout(user.getAbout());
	}
	
}
