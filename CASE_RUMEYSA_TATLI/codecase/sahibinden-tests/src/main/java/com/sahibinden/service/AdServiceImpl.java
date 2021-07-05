package com.sahibinden.service;


import com.sahibinden.common.dto.ad.AdCreateRequest;
import com.sahibinden.common.dto.ad.AdResponse;
import com.sahibinden.common.dto.ad.AdStatistic;
import com.sahibinden.common.dto.ad.DeliveryResult;
import com.sahibinden.common.dto.ad.MatchCriteria;
import com.sahibinden.common.service.AdService;


public class AdServiceImpl extends AbstractService implements AdService {

   public AdServiceImpl(String... serviceUrls) {
      super(serviceUrls);
   }


   @Override
   public AdResponse createAd(AdCreateRequest adCreateRequest) {
      return post("/ad/createAd", adCreateRequest, AdResponse.class);
   }

   @Override
   public AdStatistic getAdStatistic(String adId) {
      return get(String.format("/ad/%s/statistic", adId), AdStatistic.class);
   }

   @Override
   public DeliveryResult getWinner(MatchCriteria matchCriteria) {
      return post("/ad/getWinner", matchCriteria, DeliveryResult.class);
   }

   @Override
   public void processImpression(String deliveryId) {
      get(String.format("/ad/%s/processImpression", deliveryId), Void.class);
   }

   @Override
   public void processClick(String deliveryId) {
      get(String.format("/ad/%s/processClick", deliveryId), Void.class);
   }

   @Override
   public void deleteAll() {
      get("/ad/deleteAll", Void.class);

   }

}
