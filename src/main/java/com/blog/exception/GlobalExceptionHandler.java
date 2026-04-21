package com.blog.exception;


import com.blog.payload.ApiResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
@Getter
@Setter
public class GlobalExceptionHandler {
	// handle duplicate resource

  @ExceptionHandler(DuplicateResourceException.class)
public ResponseEntity<ApiResponseDto> handleDublicateResource(DuplicateResourceException ex){

    ApiResponseDto response = ApiResponseDto.error(ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);  // 409
}

@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ApiResponseDto> handleResourceNotFound(ResourceNotFoundException ex){
      ApiResponseDto response=ApiResponseDto.error(ex.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
}
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ApiResponseDto> handleMethodArgumentException(MethodArgumentNotValidException ex){
      Map<String ,String> response = new HashMap<>();
      ex.getBindingResult().getAllErrors().forEach((error)->{
        String fieldName=((FieldError) error).getField();
        String message=((FieldError) error).getDefaultMessage();
       response.put(fieldName,message);
      });
      return new ResponseEntity (response,HttpStatus.BAD_REQUEST);
}

@ExceptionHandler(AccessDeniedException.class)
public ResponseEntity<ApiResponseDto> handleAccessDenied(AccessDeniedException ex){
      ApiResponseDto response=ApiResponseDto.error(ex.getMessage());
      return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto> handleGlobalException(Exception ex){
      ApiResponseDto response=ApiResponseDto.error(ex.getMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
