package com.sahibinden.common.dto.ad;

public class AdStatistic {

   private String adId;
   private Long clickCount;
   private Long impressionCount;


   public String getAdId() {
      return adId;
   }

   public void setAdId(String adId) {
      this.adId = adId;
   }

   public Long getClickCount() {
      return clickCount;
   }

   public void setClickCount(Long clickCount) {
      this.clickCount = clickCount;
   }

   public Long getImpressionCount() {
      return impressionCount;
   }

   public void setImpressionCount(Long impressionCount) {
      this.impressionCount = impressionCount;
   }

   @Override
   public String toString() {
      return "AdStatistic{" +
              "adId=" + adId +
              ", clickCount=" + clickCount +
              ", impressionCount=" + impressionCount +
              '}';
   }
}
