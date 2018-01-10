package com.dongfg.project.api.repository;

import com.dongfg.project.api.entity.AtomRssInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author dongfg
 * @date 18-1-9
 */
public interface AtomRssRepository extends MongoRepository<AtomRssInfo, String> {
}
