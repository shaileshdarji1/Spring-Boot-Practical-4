package com.springboot.service.impl;

import com.springboot.dto.CategoryDto;
import com.springboot.entity.Category;
import com.springboot.entity.Product;
import com.springboot.repository.CategoryRepository;
import com.springboot.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    public final static String CATEGORY_PATH = "/home/shailesh/Documents/Java/Spring boot Practical 3/Spring Boot Practical 3/src/main/resources/static/image/category";
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Category saveCategory(Category category) {
        if (category != null) {
            LOGGER.info(category.toString());
            return categoryRepository.save(category);
        } else {
            LOGGER.warn("Category Doesn't exits");
            return null;
        }

    }

    @Override
    public Category findCategoryById(Integer menuId) {
        Category category = categoryRepository.findCategory(menuId);
        if (category != null) {
            LOGGER.info(category.toString());
            return category;
        } else {
            LOGGER.warn("Product Doesn't exits");
            return null;
        }
    }

    @Override
    public String deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findCategory(categoryId);
        if (category != null) {
            categoryRepository.delete(category);
            Path path = Paths.get(CATEGORY_PATH + File.separator + category.getImageUrl());
            try {
                Files.delete(path);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
            LOGGER.info("Category delete successfully");
            return "Category delete successfully";
        } else {
            LOGGER.warn("Category doesn't exits");
            return "Category doesn't exits";
        }
    }

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryDto categoryToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public Category dtoToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
}
