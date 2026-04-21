package com.blog.mapper;

import com.blog.entity.Category;
import com.blog.payload.CategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    // dto to entity

    public Category toEntity(CategoryDTO dto) {
        if(dto == null) return null;
        Category category = new Category();
        category.setCategoryId(dto.getId());
        category.setCategoryName(dto.getCategoryName());
        category.setCategoryDescription(dto.getCategoryDescription());
        return category;
    }

    // entity to dto
    public CategoryDTO toDTO(Category category) {
        if(category == null) return null;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());
        categoryDTO.setCategoryDescription(category.getCategoryDescription());
        return categoryDTO;
    }
    // update existing entity from DTO from update
    public void updateEntityFromDTO(CategoryDTO dto, Category category) {
       if(dto.getCategoryName()!= null)  category.setCategoryName(dto.getCategoryName());
       if(dto.getCategoryDescription()!= null)  category.setCategoryDescription(dto.getCategoryDescription());
    }
}
