package com.sahibinden.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.sahibinden.common.dto.ad.AdCreateRequest;
import com.sahibinden.common.dto.ad.AdResponse;
import com.sahibinden.common.dto.ad.Category;
import com.sahibinden.common.dto.ad.ClientType;
import com.sahibinden.common.dto.ad.MatchCriteria;
import com.sahibinden.common.service.AdService;
import com.sahibinden.util.AdUtil;


public class AdWithActualStat {

   private String adId;
   private AdCreateRequest adCreateRequest;
   private AtomicInteger clickCount;
   private AtomicInteger impressionCount;
   private Map<String, Integer> visitorImpressionCount;

   // AutoWire
   private AdService adService;

   public AdWithActualStat(AdResponse adResponse, AdCreateRequest adCreateRequest, AdService adService) {
      this.adService = adService;
      this.adCreateRequest = adCreateRequest;
      adId = adResponse.getId();
      clickCount = new AtomicInteger(0);
      impressionCount = new AtomicInteger(0);
      visitorImpressionCount = new ConcurrentHashMap<>();
   }


   public void processClick(String deliveryId, String visitorId) {
      if (deliveryId == null) return;

      adService.processClick(deliveryId);
      processVisitor(visitorId);
      impressionCount.incrementAndGet();
      clickCount.incrementAndGet();
   }

   public void processImpression(String deliveryId, String visitorId) {
      if (deliveryId == null) return;
      adService.processImpression(deliveryId);
      processVisitor(visitorId);
      impressionCount.incrementAndGet();

   }

   public MatchCriteria getMatchableCriteria(ConcurrentLinkedQueue<String> visitors) {

      MatchCriteria matchCriteria = new MatchCriteria();
      matchCriteria.setCategory(adCreateRequest.getCategoryList().get(0));
      matchCriteria.setClientType(adCreateRequest.getClientType());
      matchCriteria.setLocation(adCreateRequest.getLocations().get(0));

      String visitor = AdUtil.getRandomVisitor(visitors);
      matchCriteria.setVisitorId(visitor);

      return matchCriteria;

   }

   public MatchCriteria getUnmatchableCriteria(boolean corruptLocation, boolean corruptClientType, boolean corruptCategory, String visitorId) {

      MatchCriteria matchCriteria = new MatchCriteria();

      if (corruptCategory) {

         for (Category category : Category.values()) {
            if (!adCreateRequest.getCategoryList().contains(category)) {
               matchCriteria.setCategory(category);
               break;
            }
         }
      } else {
         matchCriteria.setCategory(adCreateRequest.getCategoryList().get(0));
      }

      //-----------------------------------------------------------------------//
      if (corruptClientType) {
         for (ClientType clientType : ClientType.values()) {
            if (!adCreateRequest.getClientType().equals(clientType)) {
               matchCriteria.setClientType(clientType);
               break;
            }
         }
      } else {
         matchCriteria.setClientType(adCreateRequest.getClientType());
      }
      //-----------------------------------------------------------------------//
      if (corruptLocation) {
         for (long i = 1; i <= 81; i++) {
            if (!adCreateRequest.getLocations().contains(i)) {
               matchCriteria.setLocation(i);
               break;
            }
         }
      } else {
         matchCriteria.setLocation(adCreateRequest.getLocations().get(0));
      }

      matchCriteria.setVisitorId(visitorId);
      return matchCriteria;

   }


   private void processVisitor(String visitorId) {
      int impressionOfVisitor = visitorImpressionCount.getOrDefault(visitorId, 0);

      visitorImpressionCount.put(visitorId, impressionOfVisitor + 1);
   }

   public boolean isFrequencyCappingExceeded(int toleranceLevel) {
      for (Integer x : visitorImpressionCount.values()) {
         if (x >= adCreateRequest.getFrequencyCapping() + toleranceLevel) {
            return true;
         }
      }
      return false;
   }

   public boolean isBudgetExceeded(int toleranceLevel) {
      long tolerance = toleranceLevel * adCreateRequest.getBidPrice();

      if (adCreateRequest.getTotalBudget() <= (clickCount.get() * adCreateRequest.getBidPrice()) - tolerance) {
         return true;
      }
      return false;
   }

   public String getId() {
      return adId;
   }


   public int getClickCount() {
      return clickCount.get();
   }

   public int getImpressionCount() {
      return impressionCount.get();
   }

   public AdCreateRequest getAdCreateRequest() {
      return adCreateRequest;
   }
}
