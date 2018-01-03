package com.dongfg.project.api.repository;

import com.dongfg.project.api.graphql.type.TotpToken;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * @author dongfg
 * @date 2017/11/22
 */
public interface TokenRepository extends MongoRepository<TotpToken, String> {
}
