package com.springboot.controller;

import com.springboot.dto.ProductDto;
import com.springboot.entity.Category;
import com.springboot.entity.Product;
import com.springboot.service.CategoryService;
import com.springboot.service.ProductService;
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
@RequestMapping("/product")
public class ProductController {
    public final static String PRODUCT_PATH = "/home/shailesh/Documents/Java/Spring boot Practical 3/Spring Boot Practical 3/src/main/resources/static/image/product";

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable Integer productId) {
        return productService.getProduct(productId);
    }

    @PostMapping("/add_product")
    public String saveProduct(@ModelAttribute ProductDto productDto, @RequestParam("imageUrl") MultipartFile file) {
        Product product = productService.dtoToProduct(productDto);
        if (file.isEmpty()) {
            return "file is empty";
        } else {
            if (product != null) {
                product.setProductId(null);
                Category category = categoryService.findCategoryById(productDto.getCategoryId());
                product.setCategory(category);
                product.setImageUrl(file.getOriginalFilename());
                Path path = Paths.get(PRODUCT_PATH+ File.separator+file.getOriginalFilename());
                try {
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                productService.saveProduct(product);
                return "Product Added Successfully";
            } else {
                return "Something went wrong!";
            }
        }
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Integer productId) {
            return productService.delete(productId);
    }
}
