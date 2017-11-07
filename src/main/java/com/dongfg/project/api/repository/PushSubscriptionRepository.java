package com.dongfg.project.api.repository;

import com.dongfg.project.api.graphql.type.PushSubscription;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author dongfg
 * @date 17-11-7
 */
public interface PushSubscriptionRepository extends MongoRepository<PushSubscription, String> {

}
