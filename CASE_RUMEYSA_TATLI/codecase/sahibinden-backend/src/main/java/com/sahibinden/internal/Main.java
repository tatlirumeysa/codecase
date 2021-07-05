package com.sahibinden.internal;

import org.springframework.boot.SpringApplication;

public class Main {

   public static void main(String[] args) throws Exception {
      int port = 12345;

      new EmbeddedMongoDb(port)
              .init();

      String[] instance_1_args = {"--server.port=9393", "--spring.data.mongodb.port=" + port};
      String[] instance_2_args = {"--server.port=9494", "--spring.data.mongodb.port=" + port};

      SpringApplication.run(Backend.class, instance_1_args);
      SpringApplication.run(BackendClone.class, instance_2_args);
   }

}
