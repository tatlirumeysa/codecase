package com.sahibinden.backend.repo.mongo;

import com.sahibinden.backend.model.AdStatisticsMappingDocument;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdStatisticsMappingRepository extends MongoRepository<AdStatisticsMappingDocument, String> {
    Optional<AdStatisticsMappingDocument> findById(String adId);
}
