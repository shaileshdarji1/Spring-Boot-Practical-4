package com.springboot.dto;


import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private String description;
    private Float price;
    private Integer categoryId;
}
