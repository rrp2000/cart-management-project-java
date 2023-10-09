package com.project.fullstack.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Collation("user")
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role;
    private String phone;
    private List<String> address = new ArrayList<>();
    private List<String> paymentInformation = new ArrayList<>();
    private List<String> ratings = new ArrayList<>();
    private List<String> reviews = new ArrayList<>();
    private Date createdAt;
}
