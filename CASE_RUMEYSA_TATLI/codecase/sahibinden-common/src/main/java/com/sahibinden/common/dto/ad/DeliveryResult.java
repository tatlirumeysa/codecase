package com.sahibinden.common.dto.ad;

public class DeliveryResult {

	private AdResponse adResponse;
	private String deliveryId;

	public DeliveryResult(AdResponse adResponse2, String string) {
		this.adResponse = adResponse2;
		this.deliveryId = string;
	}

	public DeliveryResult() {
		// TODO Auto-generated constructor stub
	}

	public AdResponse getAdResponse() {
		return adResponse;
	}

	public void setAdResponse(AdResponse adResponse) {
		this.adResponse = adResponse;
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	@Override
	public String toString() {
		return "DeliveryResult{" + "adResponse=" + adResponse + ", deliveryId='" + deliveryId + '\'' + '}';
	}
}
