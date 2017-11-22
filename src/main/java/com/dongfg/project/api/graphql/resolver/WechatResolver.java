package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.graphql.type.WechatUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dongfg
 * @date 17-11-22
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WechatResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    public String wechatLogin(String code) {
        return null;
    }

    public WechatUserInfo wechatUserInfo(String authToken) {
        return null;
    }
}
