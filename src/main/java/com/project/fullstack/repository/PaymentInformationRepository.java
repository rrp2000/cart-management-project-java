package com.project.fullstack.repository;

import com.project.fullstack.model.PaymentInformation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInformationRepository extends MongoRepository<PaymentInformation,String> {
}
