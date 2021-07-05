package com.sahibinden.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Random;

public class AbstractService {

   private final HttpClient httpClient;
   private final String[] serviceUrls;
   private final Random random = new Random();
   private final ObjectMapper objectMapper = new ObjectMapper();

   public AbstractService(String... serviceUrls) {
      this.serviceUrls = serviceUrls;
      this.httpClient = HttpClients.createDefault();
   }


   <T> T post(String url, Object request, Class<T> rClass) {

      HttpPost httpPost = new HttpPost(determineUrl(url));
      try {
         StringEntity entity = new StringEntity(objectMapper.writeValueAsString(request), ContentType.APPLICATION_JSON);
         httpPost.setEntity(entity);
      } catch (JsonProcessingException e) {
         return null;
      }
      try {
         HttpResponse httpResponse = httpClient.execute(httpPost);
         return objectMapper.readValue(httpResponse.getEntity().getContent(), rClass);
      } catch (IOException e) {
         return null;
      }
   }

   <T> T get(String url, Class<T> rClass) {
      HttpGet get = new HttpGet(determineUrl(url));
      try {
         HttpResponse httpResponse = httpClient.execute(get);
         return objectMapper.readValue(httpResponse.getEntity().getContent(), rClass);
      } catch (IOException e) {
         return null;
      }
   }


   private String determineUrl(String url) {
      int selectedIndex = random.nextInt(serviceUrls.length);
      return String.format("%s%s", serviceUrls[selectedIndex], url);
   }


}
