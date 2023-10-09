package com.project.fullstack.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;

import java.util.Date;

@Data
@Collation("paymentInformation")
public class PaymentInformation {
    @Id
    private String id;
    private String userId;
    private String cardHolderName;
    private String cardNumber;
    private Date expirationDate;
    private String cvv;
}
