package com.project.fullstack.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;

import java.util.Date;

@Data
@Collation("review")
public class Review {

    @Id
    private String id;
    private String userId;
    private String productId;
    private String reviews;
    private Date createdAt;
}
