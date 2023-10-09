package com.project.fullstack.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;

import java.util.Date;

@Data
@Collation("rating")
public class Rating {
    @Id
    private String id;
    private String userId;
    private String productId;
    private float ratings;
    private Date createdAt;
}
