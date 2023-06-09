package com.springboot.controller;

import com.springboot.dto.CategoryDto;
import com.springboot.entity.Category;
import com.springboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    public final static String CATEGORY_PATH = "/home/shailesh/Documents/Java/Spring boot Practical 3/Spring Boot Practical 3/src/main/resources/static/image/category";
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return categoryService.findAllCategory();
    }

    @GetMapping("/{categoryId}")
    public Category getCategory(@PathVariable Integer categoryId) {
        return categoryService.findCategoryById(categoryId);
    }

    @PostMapping("/add_category")
    public String saveCategory(@ModelAttribute CategoryDto categoryDto, @RequestParam("imageUrl") MultipartFile file) {
        Category category = categoryService.dtoToCategory(categoryDto);

        if (file.isEmpty()) {
            return "Image is empty";
        } else {

            if (category != null) {
                category.setImageUrl(file.getOriginalFilename());
                Path path = Paths.get(CATEGORY_PATH + File.separator + file.getOriginalFilename());
                try {
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                categoryService.saveCategory(category);
                return "Category Added Successfully";
            } else {
                return "Something went to wrong!";
            }
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public String deleteCategory(@PathVariable Integer categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
