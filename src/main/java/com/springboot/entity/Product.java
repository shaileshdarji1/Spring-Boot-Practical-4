package com.springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String name;
    private String description;
    private Float price;
    private String imageUrl;

    @ManyToOne(
            fetch = FetchType.EAGER)
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "categoryId"
    )
    private Category category;
}
