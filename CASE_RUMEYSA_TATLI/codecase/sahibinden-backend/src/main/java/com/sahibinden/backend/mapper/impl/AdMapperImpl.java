package com.sahibinden.backend.mapper.impl;

import com.sahibinden.backend.mapper.AdMapper;
import com.sahibinden.backend.model.AdDocument;
import com.sahibinden.common.dto.ad.AdCreateRequest;
import com.sahibinden.common.dto.ad.AdResponse;

public class AdMapperImpl implements AdMapper {

	@Override
	public AdDocument toAdDocument(AdCreateRequest adCreateRequest) {
		AdDocument result = new AdDocument();
		result.setBidPrice(adCreateRequest.getBidPrice());
		result.setCategoryList(adCreateRequest.getCategoryList());
		result.setClientType(adCreateRequest.getClientType());
		result.setDescription(adCreateRequest.getDescription());
		result.setFrequencyCapping(adCreateRequest.getFrequencyCapping());
		result.setLink(adCreateRequest.getLink());
		result.setLocations(adCreateRequest.getLocations());
		result.setTitle(adCreateRequest.getTitle());
		result.setTotalBudget(adCreateRequest.getTotalBudget());
		return result;
	}

	@Override
	public AdResponse toAdResponse(AdDocument adDocument) {
		AdResponse response = new AdResponse();
		response.setId(adDocument.getId());
		response.setTitle(adDocument.getTitle());
		response.setDescription(adDocument.getDescription());
		response.setLink(adDocument.getLink());
		return response;
	}
}
