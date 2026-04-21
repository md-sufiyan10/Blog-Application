package com.blog.controller;

import com.blog.entity.Category;
import com.blog.payload.ApiResponseDto;
import com.blog.payload.CategoryDTO;
import com.blog.services.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/categories")
@Data
@RequiredArgsConstructor

public class CategoryController {

    private final CategoryService categoryService;

    // create category only admin
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponseDto> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        log.info("REST request to createCategory {}", categoryDTO.getCategoryName());
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);

        return new ResponseEntity<>(
                ApiResponseDto.success("Category Created Successfully.", createdCategory)
                , HttpStatus.CREATED);
    }

    // get Category by Admin and Users both

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto> getCategoryById(@PathVariable Long id) {
     log.info("REST request to getCategoryById {}", id);
     CategoryDTO category = categoryService.getCategoryById(id);

     return  ResponseEntity.ok(ApiResponseDto.success("Category Found Successfully.", category));
    }
    // get Category All
    @GetMapping
    public ResponseEntity<ApiResponseDto> getAllCategories() {
        log.info("REST request to getAllCategories");
        List<CategoryDTO> allCategories = categoryService.getAllCategories();
        if (allCategories == null || allCategories.isEmpty()) {
            return ResponseEntity.ok(ApiResponseDto.success("No categories found.with name:"));
        }
        return ResponseEntity.ok(ApiResponseDto.success("All Categories Found", allCategories));
    }
}