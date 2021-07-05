package com.sahibinden.backend.util;

import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;

import com.sahibinden.backend.model.AdDocument;
import com.sahibinden.backend.model.AdStatisticsMappingDocument;
import com.sahibinden.backend.model.DeliveryResultMappingDocument;
import com.sahibinden.common.dto.ad.AdCreateRequest;
import com.sahibinden.common.dto.ad.AdStatistic;
import com.sahibinden.common.dto.ad.MatchCriteria;

public class AdServiceUtil {

	public String helperForUID() {
		return UUID.randomUUID().toString();
	}
	
	public static AdStatisticsMappingDocument helperForStatisticsBeforeCreation(String adId) {
		AdStatisticsMappingDocument adStatisticsMappingDocument = new AdStatisticsMappingDocument();
		adStatisticsMappingDocument.setAdId(adId);
		adStatisticsMappingDocument.setCounterClick((long) 0);
		adStatisticsMappingDocument.setCounterImpression((long) 0);
		adStatisticsMappingDocument.setMap(new HashMap<>());
		return adStatisticsMappingDocument;
	}
	
	public static AdStatistic helperForAdStatic(Optional<AdStatisticsMappingDocument> asmd) {
		AdStatisticsMappingDocument adStatisticDocument = asmd.get();
		AdStatistic adStatistic = new AdStatistic();
		adStatistic.setAdId(adStatisticDocument.getAdId());
		adStatistic.setClickCount(adStatisticDocument.getCounterClick());
		adStatistic.setImpressionCount(adStatisticDocument.getCounterImpression());
		return adStatistic;
	}
	
	public static DeliveryResultMappingDocument helperForDeliveryResult(MatchCriteria matchCriteria, AdDocument adDocument) {
		DeliveryResultMappingDocument deliveryResultMappingDocument = new DeliveryResultMappingDocument();
		deliveryResultMappingDocument.setAdDocument(adDocument);
		deliveryResultMappingDocument.setVisitorId(matchCriteria.getVisitorId());
		deliveryResultMappingDocument.setIsEvent(Boolean.FALSE);
		return deliveryResultMappingDocument;
	}

	// to manage all checks together
	// if true it is ok to create
	public static boolean helperForChecksBeforeAdCreation(AdCreateRequest adCreateRequest) {
		if (checksForLengths(adCreateRequest) && checksForBadWords(adCreateRequest)
				&& checksForIsLinkAvailable(adCreateRequest.getLink())) {
			return true;
		}
		return false;
	}

	public static boolean checksForIsLinkAvailable(String URL) {
		try {
			URLConnection connection = new java.net.URL(URL).openConnection();
			connection.connect();
			return true;
		} catch (IOException e) {
			return false;
		}
	}


	public static boolean checksForBadWords(AdCreateRequest adCreateRequest) {
		// URL resource = getClass().getClassLoader().getResource("badWords.txt");
		ClassPathResource resource = new ClassPathResource("badWords.txt");
		Scanner scanner;
		try {
			scanner = new Scanner(resource.getInputStream());
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (adCreateRequest.getTitle().contains(line) || adCreateRequest.getDescription().contains(line)) {
					return false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static boolean checksForLengths(AdCreateRequest adCreateRequest) {
		if (adCreateRequest.getTitle().length() < 10 || adCreateRequest.getTitle().length() > 30)
			return false;// title 10dan kucuk veya 30dan buyukse true
		if (adCreateRequest.getDescription().length() < 30 || adCreateRequest.getDescription().length() > 100)
			return false;
		if (adCreateRequest.getBidPrice() < 50 || adCreateRequest.getBidPrice() > 300)
			return false;
		if (adCreateRequest.getTotalBudget() < adCreateRequest.getBidPrice() * 10)
			return false;
		if (adCreateRequest.getFrequencyCapping() < 6 || adCreateRequest.getFrequencyCapping() > 24)
			return false;
		if (adCreateRequest.getLocations().stream().anyMatch(locations -> locations < 1 || locations > 81))
			return false;
		if (adCreateRequest.getClientType() == null)
			return false;
		if (adCreateRequest.getCategoryList().size() < 1)
			return false;

		return true;
	}

}
