package com.springboot.service;

import com.springboot.dto.ProductDto;
import com.springboot.entity.Product;

import java.util.List;

public interface ProductService {
    public Product getProduct(Integer productId);

    public Product saveProduct(Product product);

    public String delete(Integer productId);

    public List<Product> getProducts();

    public Product dtoToProduct(ProductDto productDto);

    public ProductDto productToDto(Product product);
}
