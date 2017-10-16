package com.dongfg.project.api.repository;

import com.dongfg.project.api.graphql.type.Sms;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author dongfg
 * @date 2017/10/15
 */
public interface SmsRepository extends MongoRepository<Sms, String> {
}
