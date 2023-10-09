package com.project.fullstack.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;

@Data
@Collation("address")
public class Address {

    @Id
    private String id;
    private String userId;
    private String name;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
}
