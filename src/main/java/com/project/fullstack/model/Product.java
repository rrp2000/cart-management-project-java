package com.project.fullstack.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;

import java.util.Date;

@Data
@Collation("product")
public class Product {
    @Id
    private String id;
    private String title;
    private String description;
    private int price;
    private int discountedPrice;
    private int discountPercent;
    private int quantity;
    private String brand;
    private Date createdAt;
}
