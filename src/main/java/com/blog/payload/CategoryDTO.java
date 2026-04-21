package com.blog.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long id;

    @NotBlank(message="Category name is Required")
    @Size(min=3, max=100 , message = "Category name must be between 3 and 100 characters.")
    private String categoryName;

    @Size(max=500, message = "Description cannot exceed 500 characters..")
    private String categoryDescription;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
