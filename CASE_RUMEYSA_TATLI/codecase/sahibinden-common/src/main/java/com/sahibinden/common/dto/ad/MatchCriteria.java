package com.sahibinden.common.dto.ad;

public class MatchCriteria {

   private ClientType clientType;
   private Category category;
   private String visitorId;
   private Long location;

   public ClientType getClientType() {
      return clientType;
   }

   public void setClientType(ClientType clientType) {
      this.clientType = clientType;
   }

   public Category getCategory() {
      return category;
   }

   public void setCategory(Category category) {
      this.category = category;
   }

   public String getVisitorId() {
      return visitorId;
   }

   public void setVisitorId(String visitorId) {
      this.visitorId = visitorId;
   }

   public Long getLocation() {
      return location;
   }

   public void setLocation(Long location) {
      this.location = location;
   }


   @Override
   public String toString() {
      return "MatchCriteria{" +
              "clientType=" + clientType +
              ", category=" + category +
              ", visitorId='" + visitorId + '\'' +
              ", location=" + location +
              '}';
   }
}
