package com.sahibinden.backend.model;

import com.sahibinden.common.dto.ad.Category;
import com.sahibinden.common.dto.ad.ClientType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "ad_collection")
public class AdDocument {

   @Id
   private String id;

   private ClientType clientType;

   private List<Category> categoryList;

   private Long bidPrice;

   private Long totalBudget;

   private Long frequencyCapping;

   private List<Long> locations;

   private String title;

   private String description;

   private String link;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public ClientType getClientType() {
      return clientType;
   }

   public void setClientType(ClientType clientType) {
      this.clientType = clientType;
   }

   public List<Category> getCategoryList() {
      return categoryList;
   }

   public void setCategoryList(List<Category> categoryList) {
      this.categoryList = categoryList;
   }

   public Long getBidPrice() {
      return bidPrice;
   }

   public void setBidPrice(Long bidPrice) {
      this.bidPrice = bidPrice;
   }

   public Long getTotalBudget() {
      return totalBudget;
   }

   public void setTotalBudget(Long totalBudget) {
      this.totalBudget = totalBudget;
   }

   public Long getFrequencyCapping() {
      return frequencyCapping;
   }

   public void setFrequencyCapping(Long frequencyCapping) {
      this.frequencyCapping = frequencyCapping;
   }

   public List<Long> getLocations() {
      return locations;
   }

   public void setLocations(List<Long> locations) {
      this.locations = locations;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getLink() {
      return link;
   }

   public void setLink(String link) {
      this.link = link;
   }
}
