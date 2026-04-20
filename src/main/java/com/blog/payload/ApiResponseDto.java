package com.blog.payload;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto {
	 private boolean success;
	  private String message;
	private LocalDateTime timestamp;
	  private Object data;

	// Factory method for SUCCESS with data
	  public static ApiResponseDto success(String message, Object data) {
	        return new ApiResponseDto(true, message, LocalDateTime.now(),data);
	    }

		//  // Factory method for SUCCESS without data
	  public static ApiResponseDto success(String message) {
		  return new ApiResponseDto(true,message,LocalDateTime.now(),null);
	  }

	// Factory method for ERROR
	public static ApiResponseDto error(String message) {
		  return new ApiResponseDto(false,message,LocalDateTime.now(),null);
	}
	// Factory method for ERROR with data

	public static ApiResponseDto error(String message, Object data) {
		  return new ApiResponseDto(false,message,LocalDateTime.now(),data);
	}
}
