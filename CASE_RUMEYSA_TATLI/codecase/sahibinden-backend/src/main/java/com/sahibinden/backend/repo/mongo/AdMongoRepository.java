package com.sahibinden.backend.repo.mongo;

import com.sahibinden.backend.model.AdDocument;
import com.sahibinden.common.dto.ad.Category;
import com.sahibinden.common.dto.ad.ClientType;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdMongoRepository extends MongoRepository<AdDocument, String> {

	List<AdDocument> findByClientTypeAndCategoryListContainsAndLocationsContains(ClientType clientType, Category category, Long location);

}
