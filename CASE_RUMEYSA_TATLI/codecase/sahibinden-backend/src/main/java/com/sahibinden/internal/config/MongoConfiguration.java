package com.sahibinden.internal.config;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.sahibinden.backend.repo.mongo")
public class MongoConfiguration {
}
