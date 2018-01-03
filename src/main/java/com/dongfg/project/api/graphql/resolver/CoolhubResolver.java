package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.entity.WechatSession;
import com.dongfg.project.api.graphql.payload.SessionPayload;
import com.dongfg.project.api.graphql.type.TotpToken;
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

    public SessionPayload wechatLogin(String code) {
        return wechatService.wechatLogin(code);
    }

    public List<TotpToken> totpTokens(String authToken) {
        checkSession(authToken);
        try {
            return coolhubService.tokens();
        } finally {
            SecurityContextHolder.removeCurrent();
        }
    }

    public TotpToken createToken(String authToken, TotpToken input) {
        checkSession(authToken);
        try {
            return coolhubService.createToken(input);
        } finally {
            SecurityContextHolder.removeCurrent();
        }
    }

    public TotpToken updateToken(String authToken, TotpToken input) {
        checkSession(authToken);
        try {
            return coolhubService.updateToken(input);
        } finally {
            SecurityContextHolder.removeCurrent();
        }
    }

    public TotpToken removeToken(String authToken, String id) {
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
