package com.blog.services.impl;

import com.blog.entity.Category;
import com.blog.exception.DuplicateResourceException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.mapper.CategoryMapper;
import com.blog.payload.CategoryDTO;
import com.blog.repository.CategoryRepository;
import com.blog.services.CategoryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.cfg.MapperBuilder;

import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        log.info("creating new Category {}", categoryDTO.getCategoryName());
        // if check category is already there or not
        if(categoryRepository.existsByName(categoryDTO.getCategoryName())) {
            log.error("Category with name {} already exists", categoryDTO.getCategoryName());
            throw new DuplicateResourceException("Category", "name", categoryDTO.getCategoryName());
        }
            // convert dto to entity
           Category category= categoryMapper.toEntity(categoryDTO);
          Category  savedCategory= categoryRepository.save(category);
          log.info("created category Successfully With ID: {}",savedCategory.getCategoryId() );
         return   categoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        log.info("getting Category with ID {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return categoryMapper.toDTO(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        log.info("Fetching all categories");
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()) {
            log.info("No categories found in DB.");
        }
        return categories.stream().map(categoryMapper::toDTO).toList();
    }
    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        log.info("updating category with ID {}", id);
        // if category already exists
        Category existsCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        // check names is changed and new name already exists
        boolean isNamedChanged= !existsCategory.getCategoryName().equals(categoryDTO.getCategoryName());
        if(isNamedChanged && categoryRepository.existsByName(categoryDTO.getCategoryName())) {
            log.error("Category with name {} already exists", categoryDTO.getCategoryName());
            throw new DuplicateResourceException("Category", "name", categoryDTO.getCategoryName());
        }
        // update entity with new values
        categoryMapper.updateEntityFromDTO(categoryDTO, existsCategory);
        // saved updated category
       Category updatedCategory= categoryRepository.save(existsCategory);
       log.info("updated category Successfully With ID: {}",updatedCategory.getCategoryId() );
        return categoryMapper.toDTO(updatedCategory);
    }
    @Override
    public void deleteCategoryById(Long id) {
        log.info("deleting category with ID {}", id);
        // if category is already present
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.deleteById(id);
        log.info(" Category deleted successfully with ID: {}", id);
    }
}
