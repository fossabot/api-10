package com.dongfg.project.api.repository;

import com.dongfg.project.api.graphql.type.Sms;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SmsRepository extends MongoRepository<Sms, String> {
}
