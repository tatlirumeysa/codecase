package com.sahibinden.backend.repo.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.sahibinden.backend.model.AdStatisticsMappingDocument;
import com.sahibinden.backend.repo.mongo.AdStatisticsMappingRepository;
import com.sahibinden.backend.repo.mongo.GeneralStatisticsRepository;


@Service
public class GeneralStatisticsRepositoryImpl implements GeneralStatisticsRepository {

	private final MongoTemplate mongoTemplate;
	public static final String CLICK = "counterClick";
	public static final String IMPRESSION = "counterImpression";
	public static final String ID = "adId";

	AdStatisticsMappingRepository adStatisticsMappingRepository;

	@Autowired
	public GeneralStatisticsRepositoryImpl(MongoTemplate mongoTemplate,
			AdStatisticsMappingRepository adStatisticsMappingRepository) {
		this.mongoTemplate = mongoTemplate;
		this.adStatisticsMappingRepository = adStatisticsMappingRepository;
	}

	@Override
	public void updateStatisticsForImpression(String adId) {

		Query query = Query.query(Criteria.where(ID).is(adId));
		Update update = new Update().inc(IMPRESSION, 1);

		mongoTemplate.updateFirst(query, update, AdStatisticsMappingDocument.class);
	}

	@Override
	public void updateStatisticsForClick(String adId) {

		Query query = Query.query(Criteria.where(ID).is(adId));
		Update update = new Update().inc(CLICK, 1);

		mongoTemplate.updateFirst(query, update, AdStatisticsMappingDocument.class);
	}

	@Override
	public void updateStatisticsForImpressionWithVisitor(String adId, String visitorId) {
		Optional<AdStatisticsMappingDocument> asmd = adStatisticsMappingRepository.findById(adId);
		if (asmd.isPresent()) {
			synchronized (this.getClass()) {
				AdStatisticsMappingDocument adStatisticsMappingDocument = asmd.get();
				try {
					adStatisticsMappingDocument.getMap().put(visitorId,
							adStatisticsMappingDocument.getMap().get(visitorId) + 1);
				} catch (NullPointerException e) {
					adStatisticsMappingDocument.getMap().put(visitorId, 1);
				}
				adStatisticsMappingRepository.save(adStatisticsMappingDocument);
			}
		}
	}

}
