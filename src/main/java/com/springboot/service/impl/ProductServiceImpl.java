package com.springboot.service.impl;

import com.springboot.dto.ProductDto;
import com.springboot.entity.Product;
import com.springboot.repository.ProductRepository;
import com.springboot.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    public final static String PRODUCT_PATH = "/home/shailesh/Documents/Java/Spring boot Practical 3/Spring Boot Practical 3/src/main/resources/static/image/product";
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Product getProduct(Integer productId) {
        Product product = productRepository.findProduct(productId);
        if (product != null) {
            LOGGER.info(product.toString());
            return product;
        } else {
            LOGGER.warn("Product Doesn't exits");
            return null;
        }
    }

    @Override
    public Product saveProduct(Product product) {
        if (product != null) {
            LOGGER.info(product.toString());
            return productRepository.save(product);
        } else {
            LOGGER.warn("Product doesn't exists");
            return null;
        }
    }

    @Override
    public String delete(Integer productId) {
        Product product = productRepository.findProduct(productId);
        if (product != null) {
            productRepository.delete(product);
            Path path = Paths.get(PRODUCT_PATH + File.separator + product.getImageUrl());
            try {
                Files.delete(path);
                LOGGER.info("Product Delete Successfully");
                return "Product Delete Successfully";

            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage());
                return e.getMessage();

            }
        } else {
            LOGGER.warn("Product doesn't exits");
            return "Product doesn't exits";
        }
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product dtoToProduct(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

    @Override
    public ProductDto productToDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }
}
