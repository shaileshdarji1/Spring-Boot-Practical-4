package com.springboot.service;

import com.springboot.dto.CategoryDto;
import com.springboot.entity.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> findAllCategory();

    public Category saveCategory(Category category);

    public Category findCategoryById(Integer categoryId);

    public String deleteCategory(Integer categoryId);

    public CategoryDto categoryToDto(Category category);

    public Category dtoToCategory(CategoryDto categoryDto);
}
