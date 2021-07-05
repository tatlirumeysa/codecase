package com.sahibinden.backend.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "ad_statistics_mapping_document")
public class AdStatisticsMappingDocument {

    @Id
    private String adId;
    private Long counterImpression;
    private Long counterClick;
    private Map<String, Integer> map;

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }

	public Long getCounterImpression() {
		return counterImpression;
	}

	public void setCounterImpression(Long counterImpression) {
		this.counterImpression = counterImpression;
	}

	public Long getCounterClick() {
		return counterClick;
	}

	public void setCounterClick(Long counterClick) {
		this.counterClick = counterClick;
	}
}
