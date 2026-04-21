package com.blog.services;

import com.blog.payload.CategoryDTO;

import java.util.List;

public interface CategoryService {
    // create category
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    // get category by id
    CategoryDTO getCategoryById(Long id);

    // update category by id
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    // get all category
   List<CategoryDTO> getAllCategories();

   // delete category by id
   void deleteCategoryById(Long id);
}
