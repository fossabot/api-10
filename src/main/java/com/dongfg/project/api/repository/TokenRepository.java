package com.dongfg.project.api.repository;

import com.dongfg.project.api.graphql.type.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<Token, String> {
}
