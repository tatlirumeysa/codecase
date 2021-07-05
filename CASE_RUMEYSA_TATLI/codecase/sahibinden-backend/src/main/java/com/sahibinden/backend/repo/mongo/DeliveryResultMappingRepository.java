package com.sahibinden.backend.repo.mongo;

import com.sahibinden.backend.model.DeliveryResultMappingDocument;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryResultMappingRepository extends MongoRepository<DeliveryResultMappingDocument, String> {
}
