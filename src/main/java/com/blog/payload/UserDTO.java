package com.blog.payload;


import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	@Id
	private Long id;
    @NotBlank(message = "Name is Required")
    @Size(min=3, max=100)
	private String name;
	
	 @NotBlank(message = "Email is Required")
	 @Email(message = "Invalid Email Formate")
	private String email;
	
	 @NotBlank(message = "Password is Required")
	 @Size(min=6, max=100)
	private String password;

	 @Size(max=100 , message = "About Can not exceed 500 character")
	private String about;



	// - These validations work with @Valid in controller
}
