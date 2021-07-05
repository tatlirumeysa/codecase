package com.sahibinden.tests;



import java.util.Arrays;

import com.sahibinden.common.dto.ad.AdCreateRequest;
import com.sahibinden.common.dto.ad.AdResponse;
import com.sahibinden.common.service.AdService;
import com.sahibinden.service.AdServiceImpl;
import com.sahibinden.util.AdUtil;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestCreateAd {

    private AdService adService;

    @Before
    public void init() {
        adService = new AdServiceImpl("http://localhost:9494", "http://localhost:9393");
        adService.deleteAll();
    }

    @Test
    public void testCreateAd() {
        AdCreateRequest adCreateRequest = AdUtil.getRandomAdCreateRequest();
        AdResponse adResponse = adService.createAd(adCreateRequest);
        Assert.assertNotNull(adResponse.getId());
    }

    @Test
    public void testCreateAdWithBadWords() {

        AdCreateRequest adCreateRequest = AdUtil.getRandomAdCreateRequest();
        int randInt = RandomUtils.nextInt(0, 2400);
        adCreateRequest.setTitle(RandomStringUtils.randomAlphanumeric(4) + " " + AdUtil.badWords[randInt] + " " + RandomStringUtils.randomAlphanumeric(4));
        AdResponse adResponse = adService.createAd(adCreateRequest);
        Assert.assertNull(adResponse);


        adCreateRequest = AdUtil.getRandomAdCreateRequest();
        randInt = RandomUtils.nextInt(0, 2400);
        adCreateRequest.setDescription(RandomStringUtils.randomAlphanumeric(4) + AdUtil.badWords[randInt] + RandomStringUtils.randomAlphanumeric(4));
        adResponse = adService.createAd(adCreateRequest);
        Assert.assertNull(adResponse);

    }

    @Test
    public void testCreateAdWithBadTitle() {
        AdCreateRequest adCreateRequest = AdUtil.getRandomAdCreateRequest();

        adCreateRequest.setTitle(RandomStringUtils.randomAlphabetic(5));
        AdResponse adResponse = adService.createAd(adCreateRequest);
        Assert.assertNull(adResponse);

        adCreateRequest.setTitle(RandomStringUtils.randomAlphabetic(50));
        adResponse = adService.createAd(adCreateRequest);
        Assert.assertNull(adResponse);
    }


    @Test
    public void testCreateAdWithBadDescription() {
        AdCreateRequest adCreateRequest = AdUtil.getRandomAdCreateRequest();

        adCreateRequest.setDescription(RandomStringUtils.randomAlphabetic(20));
        AdResponse adResponse = adService.createAd(adCreateRequest);
        Assert.assertNull(adResponse);

        adCreateRequest.setTitle(RandomStringUtils.randomAlphabetic(500));
        adResponse = adService.createAd(adCreateRequest);
        Assert.assertNull(adResponse);
    }


    @Test
    public void testCreateAdWithBadBidPrice() {
        AdCreateRequest adCreateRequest = AdUtil.getRandomAdCreateRequest();

        adCreateRequest.setBidPrice(40L);
        AdResponse adResponse = adService.createAd(adCreateRequest);
        Assert.assertNull(adResponse);

        adCreateRequest.setBidPrice(400L);
        adResponse = adService.createAd(adCreateRequest);
        Assert.assertNull(adResponse);
    }


    @Test
    public void testCreateAdWithBadTotalBudget() {

        AdCreateRequest adCreateRequest = AdUtil.getRandomAdCreateRequest();

        adCreateRequest.setBidPrice(40L);
        adCreateRequest.setTotalBudget(40L * 9);
        AdResponse adResponse = adService.createAd(adCreateRequest);
        Assert.assertNull(adResponse);
    }


    @Test
    public void testCreateAdWithBadFrequencyCapping() {
        AdCreateRequest adCreateRequest = AdUtil.getRandomAdCreateRequest();

        adCreateRequest.setFrequencyCapping(1L);
        AdResponse adResponse = adService.createAd(adCreateRequest);
        Assert.assertNull(adResponse);


        adCreateRequest.setFrequencyCapping(100L);
        adResponse = adService.createAd(adCreateRequest);
        Assert.assertNull(adResponse);
    }


    @Test
    public void testCreateAdWithBadLocation() {
        AdCreateRequest adCreateRequest = AdUtil.getRandomAdCreateRequest();

        adCreateRequest.setLocations(Arrays.asList(0L, 1L, 2L, 3L, 4L, 5L, 92L));
        AdResponse adResponse = adService.createAd(adCreateRequest);
        Assert.assertNull(adResponse);

    }

}
