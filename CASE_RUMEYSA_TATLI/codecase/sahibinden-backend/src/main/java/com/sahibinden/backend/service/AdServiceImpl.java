package com.sahibinden.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahibinden.backend.mapper.AdMapper;
import com.sahibinden.backend.mapper.impl.AdMapperImpl;
import com.sahibinden.backend.model.AdDocument;
import com.sahibinden.backend.model.AdStatisticsMappingDocument;
import com.sahibinden.backend.model.DeliveryResultMappingDocument;
import com.sahibinden.backend.repo.mongo.AdMongoRepository;
import com.sahibinden.backend.repo.mongo.AdStatisticsMappingRepository;
import com.sahibinden.backend.repo.mongo.DeliveryResultMappingRepository;
import com.sahibinden.backend.repo.mongo.GeneralStatisticsRepository;
import com.sahibinden.backend.util.AdServiceUtil;
import com.sahibinden.common.dto.ad.AdCreateRequest;
import com.sahibinden.common.dto.ad.AdResponse;
import com.sahibinden.common.dto.ad.AdStatistic;
import com.sahibinden.common.dto.ad.DeliveryResult;
import com.sahibinden.common.dto.ad.MatchCriteria;
import com.sahibinden.common.service.AdService;

/*
 * 
 *@author Rumeysa Tatlı 
 * 
 */

@Service
public class AdServiceImpl implements AdService {

	private static AdMapper mapper = new AdMapperImpl();

	@Autowired
	AdMongoRepository adMongoRepository;

	@Autowired
	DeliveryResultMappingRepository deliveryRepository;

	@Autowired
	AdStatisticsMappingRepository adStatisticRepository;

	@Autowired
	GeneralStatisticsRepository generalStatisticsRepository;

	@Override
	public AdResponse createAd(AdCreateRequest adCreateRequest) {
		System.out.println("createAd is being invoking.. ");
		if (!AdServiceUtil.helperForChecksBeforeAdCreation(adCreateRequest)) {
			System.out.println("Ad is not created! ");
			return null; // in case of no creation for ad
		}
		AdDocument adDocument = adMongoRepository.save(mapper.toAdDocument(adCreateRequest));
		adStatisticRepository.save(AdServiceUtil.helperForStatisticsBeforeCreation(adDocument.getId()));
		System.out.println("Ad is created! ");
		return mapper.toAdResponse(adDocument);
	}

	@Override
	public AdStatistic getAdStatistic(String adId) {
		System.out.println("getAdStatistic is being invoking.. ");
		Optional<AdStatisticsMappingDocument> asmd = adStatisticRepository.findById(adId);
		if (!asmd.isPresent()) {
			System.out.println("AdStatistic is not found! ");
			return null;
		}
		AdStatistic adStatistic = AdServiceUtil.helperForAdStatic(asmd);
		System.out.println("AdStatistic is found! ");
		return adStatistic;
	}

	@Override
	public DeliveryResult getWinner(MatchCriteria matchCriteria) {
		System.out.println("getWinner is being invoking.. ");
		DeliveryResult deliveryResult = new DeliveryResult();
		// getting ad that matches with criteria
		List<AdDocument> adDocumentList = adMongoRepository.findByClientTypeAndCategoryListContainsAndLocationsContains(
				matchCriteria.getClientType(), matchCriteria.getCategory(), matchCriteria.getLocation());

		if (!adDocumentList.isEmpty()) {
			for (AdDocument adDocument : adDocumentList) {
				if (helperForBudget(adDocument) && helperForVisitor(adDocument, matchCriteria.getVisitorId())) {
					// Create delivery
					DeliveryResultMappingDocument deliveryResultMappingDocument = AdServiceUtil
							.helperForDeliveryResult(matchCriteria, adDocument);
					deliveryRepository.save(deliveryResultMappingDocument);
					deliveryResult.setDeliveryId(deliveryResultMappingDocument.getId());
					deliveryResult.setAdResponse(mapper.toAdResponse(adDocument));
					System.out.println("DeliveryResult is mapped! ");
				}
			}
		}
		return deliveryResult;
	}

	@Override
	public void processImpression(String deliveryId) {
		System.out.println("processImpression is being invoking.. ");
		Optional<DeliveryResultMappingDocument> ddo = deliveryRepository.findById(deliveryId);

		if (ddo.isPresent() && Boolean.FALSE.equals(ddo.get().getIsEvent())) {
			DeliveryResultMappingDocument deliveryResultMappingDocument = ddo.get();
			deliveryRepository.save(deliveryResultMappingDocument);
			helperForProcessClickAndImpression(deliveryResultMappingDocument, false);
		}
	}

	@Override
	public void processClick(String deliveryId) {
		System.out.println("processClick is being invoking.. ");
		Optional<DeliveryResultMappingDocument> ddo = deliveryRepository.findById(deliveryId);
		if (ddo.isPresent() && Boolean.FALSE.equals(ddo.get().getIsEvent())) {
			DeliveryResultMappingDocument deliveryResultMappingDocument = ddo.get();
			if (helperForBudget(deliveryResultMappingDocument.getAdDocument())) {
				helperForProcessClickAndImpression(deliveryResultMappingDocument, true);
				deliveryRepository.save(deliveryResultMappingDocument);
			}

		}
	}

	@Override
	public void deleteAll() {
		System.out.println("deleteAll is being invoking.. ");
		adMongoRepository.deleteAll();
		adStatisticRepository.deleteAll();
		deliveryRepository.deleteAll();
	}

	private boolean helperForVisitor(AdDocument adDocument, String visitorId) {
		Optional<AdStatisticsMappingDocument> asmd = adStatisticRepository.findById(adDocument.getId());
		if (asmd.isPresent()) {
			AdStatisticsMappingDocument adStatisticsMappingDocument = asmd.get();
			return adStatisticsMappingDocument.getMap().get(visitorId) == null
					|| adStatisticsMappingDocument.getMap().get(visitorId) < adDocument.getFrequencyCapping();
		}
		return true;
	}

	private boolean helperForBudget(AdDocument adDocument) {
		Optional<AdStatisticsMappingDocument> asmd = adStatisticRepository.findById(adDocument.getId());
		if (asmd.isPresent()) {
			AdStatisticsMappingDocument adStatisticsMappingDocument = asmd.get();
			if (adDocument.getTotalBudget() > ((adStatisticsMappingDocument.getCounterClick().intValue() + 1)
					* adDocument.getBidPrice())) {
				return true;
			}
		}
		return false;
	}

	// flagForClick is being used for switching
	private void helperForProcessClickAndImpression(DeliveryResultMappingDocument deliveryResultMappingDocument,
			boolean flagForClick) {
		if (flagForClick) {
			System.out.println("flagForClick is being used.. ");
			generalStatisticsRepository.updateStatisticsForClick(deliveryResultMappingDocument.getAdDocument().getId());
		}
		deliveryResultMappingDocument.setIsEvent(Boolean.TRUE);
		generalStatisticsRepository
				.updateStatisticsForImpression(deliveryResultMappingDocument.getAdDocument().getId());
		generalStatisticsRepository.updateStatisticsForImpressionWithVisitor(
				deliveryResultMappingDocument.getAdDocument().getId(), deliveryResultMappingDocument.getVisitorId());
	}
}
