package com.dongfg.project.api.repository;

import com.dongfg.project.api.entity.WechatSession;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author dongfg
 * @date 2017/11/22
 */
public interface WechatSessionRepository extends MongoRepository<WechatSession, String> {
}
