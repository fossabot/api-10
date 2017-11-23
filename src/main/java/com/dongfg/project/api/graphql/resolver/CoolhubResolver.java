package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.entity.WechatSession;
import com.dongfg.project.api.graphql.type.Token;
import com.dongfg.project.api.service.CoolhubService;
import com.dongfg.project.api.service.WechatService;
import com.dongfg.project.api.util.SecurityContextHolder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dongfg
 * @date 17-11-22
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CoolhubResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @NonNull
    private WechatService wechatService;

    @NonNull
    private CoolhubService coolhubService;

    public List<Token> loadTokens(String authToken) {
        checkSession(authToken);
        try {
            return coolhubService.loadTokens();
        } finally {
            SecurityContextHolder.removeCurrent();
        }
    }

    public Token addToken(String authToken, Token token) {
        checkSession(authToken);
        try {
            return coolhubService.addToken(token);
        } finally {
            SecurityContextHolder.removeCurrent();
        }
    }

    public Token updateToken(String authToken, String id, Token token) {
        checkSession(authToken);
        try {
            return coolhubService.updateToken(id, token);
        } finally {
            SecurityContextHolder.removeCurrent();
        }
    }

    public Token removeToken(String authToken, String id) {
        checkSession(authToken);
        try {
            return coolhubService.removeToken(id);
        } finally {
            SecurityContextHolder.removeCurrent();
        }
    }

    private void checkSession(String authToken) {
        WechatSession wechatSession = wechatService.loadSession(authToken);
        if (wechatSession == null) {
            throw new RuntimeException("error authToken");
        }
        SecurityContextHolder.setCurrent(wechatSession.getOpenId());
    }
}
