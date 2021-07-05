package com.sahibinden.tests;


import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.LongStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sahibinden.common.dto.ad.AdCreateRequest;
import com.sahibinden.common.dto.ad.AdResponse;
import com.sahibinden.common.dto.ad.AdStatistic;
import com.sahibinden.common.dto.ad.DeliveryResult;
import com.sahibinden.common.dto.ad.MatchCriteria;
import com.sahibinden.common.service.AdService;
import com.sahibinden.service.AdServiceImpl;
import com.sahibinden.util.AdUtil;
import com.sahibinden.util.AdWithActualStat;

public class TestBudgetAndFrequencyCapping {


   private AdService adService;

   @Before
   public void init() {
      adService = new AdServiceImpl("http://localhost:9494","http://localhost:9393");
      adService.deleteAll();
   }

   @Test
   public void testClickToAd() {
      ConcurrentLinkedQueue<String> visitors = AdUtil.generateRandomVisitors(1);
      AdCreateRequest adCreateRequest = AdUtil.getRandomAdCreateRequest();
      AdResponse adResponse = adService.createAd(adCreateRequest);

      AdWithActualStat adWithActualStat = new AdWithActualStat(adResponse, adCreateRequest, adService);
      MatchCriteria matchCriteria = adWithActualStat.getMatchableCriteria(visitors);
      DeliveryResult deliveryResult = adService.getWinner(matchCriteria);

      Assert.assertNotNull(deliveryResult.getDeliveryId());

      adWithActualStat.processClick(deliveryResult.getDeliveryId(), matchCriteria.getVisitorId());

      AdStatistic adStatistic = adService.getAdStatistic(adResponse.getId());

      Assert.assertEquals(1, (long) adStatistic.getClickCount());
      Assert.assertEquals(1, (long) adStatistic.getImpressionCount());

   }


   @Test
   public void testImpressionToAd() {
      ConcurrentLinkedQueue<String> visitors = AdUtil.generateRandomVisitors(1);
      AdCreateRequest adCreateRequest = AdUtil.getRandomAdCreateRequest();
      AdResponse adResponse = adService.createAd(adCreateRequest);

      AdWithActualStat adWithActualStat = new AdWithActualStat(adResponse, adCreateRequest, adService);
      MatchCriteria matchCriteria = adWithActualStat.getMatchableCriteria(visitors);
      DeliveryResult deliveryResult = adService.getWinner(matchCriteria);

      Assert.assertNotNull(deliveryResult.getDeliveryId());

      adWithActualStat.processImpression(deliveryResult.getDeliveryId(), matchCriteria.getVisitorId());

      AdStatistic adStatistic = adService.getAdStatistic(adResponse.getId());

      Assert.assertEquals(1, (long) adStatistic.getImpressionCount());

   }


   @Test
   public void testFrequencyCapping() {
      ConcurrentLinkedQueue<String> visitors = AdUtil.generateRandomVisitors(1);
      AdCreateRequest adCreateRequest = AdUtil.getRandomAdCreateRequest();
      AdResponse adResponse = adService.createAd(adCreateRequest);

      AdWithActualStat adWithActualStat = new AdWithActualStat(adResponse, adCreateRequest, adService);


      LongStream.range(0, adCreateRequest.getFrequencyCapping()).forEach(i ->
              {
                 MatchCriteria matchCriteria = adWithActualStat.getMatchableCriteria(visitors);

                 DeliveryResult deliveryResult = adService.getWinner(matchCriteria);

                 Assert.assertNotNull(deliveryResult.getDeliveryId());

                 adWithActualStat.processImpression(deliveryResult.getDeliveryId(), matchCriteria.getVisitorId());

                 visitors.add(matchCriteria.getVisitorId());
              }
      );


      AdStatistic adStatistic = adService.getAdStatistic(adResponse.getId());

      Assert.assertEquals(adCreateRequest.getFrequencyCapping(), adStatistic.getImpressionCount());

      DeliveryResult deliveryResult = adService.getWinner(adWithActualStat.getMatchableCriteria(visitors));

      Assert.assertNull(deliveryResult.getDeliveryId());
      Assert.assertNull(deliveryResult.getAdResponse());

   }

   @Test
   public void testBudget() {
      ConcurrentLinkedQueue<String> visitors = AdUtil.generateRandomVisitors(200);
      AdCreateRequest adCreateRequest = AdUtil.getRandomAdCreateRequest();
      AdResponse adResponse = adService.createAd(adCreateRequest);

      AdWithActualStat adWithActualStat = new AdWithActualStat(adResponse, adCreateRequest, adService);

      long clicksNeededForBudget = (adCreateRequest.getTotalBudget() / adCreateRequest.getBidPrice()) + 1;


      LongStream.range(0, clicksNeededForBudget).forEach(i ->
              {
                 MatchCriteria matchCriteria = adWithActualStat.getMatchableCriteria(visitors);

                 DeliveryResult deliveryResult = adService.getWinner(matchCriteria);

                 adWithActualStat.processClick(deliveryResult.getDeliveryId(), matchCriteria.getVisitorId());

                 visitors.add(matchCriteria.getVisitorId());
              }
      );

      AdStatistic adStatistic = adService.getAdStatistic(adResponse.getId());

      Assert.assertEquals(adWithActualStat.getClickCount(), adStatistic.getImpressionCount().intValue());

      DeliveryResult deliveryResult = adService.getWinner(adWithActualStat.getMatchableCriteria(visitors));

      Assert.assertNull(deliveryResult.getDeliveryId());
      Assert.assertNull(deliveryResult.getAdResponse());
   }
}
