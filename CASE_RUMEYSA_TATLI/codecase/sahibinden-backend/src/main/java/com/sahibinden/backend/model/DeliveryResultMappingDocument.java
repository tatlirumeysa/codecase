package com.sahibinden.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "delivery_result_mapping_document")
public class DeliveryResultMappingDocument {

	@Id
	private String id;
	private String visitorId;
	private AdDocument adDocument;
	private Boolean isEvent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AdDocument getAdDocument() {
		return adDocument;
	}

	public void setAdDocument(AdDocument adDocument) {
		this.adDocument = adDocument;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public Boolean getIsEvent() {
		return isEvent;
	}

	public void setIsEvent(Boolean isEvent) {
		this.isEvent = isEvent;
	}

}
