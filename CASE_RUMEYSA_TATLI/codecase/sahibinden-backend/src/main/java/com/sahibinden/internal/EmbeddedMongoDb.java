package com.sahibinden.internal;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;

public class EmbeddedMongoDb {

   private int port;

   public EmbeddedMongoDb(int port) {
      this.port = port;
   }

   public void init() throws Exception {
      MongodStarter starter = MongodStarter.getDefaultInstance();

      String bindIp = "localhost";


      IMongodConfig mongodConfig = new MongodConfigBuilder()
              .version(Version.Main.PRODUCTION)
              .net(new Net(bindIp, port, false))
              .build();

      MongodExecutable mongodExecutable = null;
      mongodExecutable = starter.prepare(mongodConfig);
      mongodExecutable.start();

   }
}
