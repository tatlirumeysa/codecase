package com.sahibinden.internal;

import com.sahibinden.internal.config.MongoConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {EmbeddedMongoAutoConfiguration.class})
@ComponentScan(value = "com.sahibinden.backend")
@Import(value = MongoConfiguration.class)
public class Backend {


}
