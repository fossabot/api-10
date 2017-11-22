package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.graphql.type.Token;
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
    public List<Token> loadTokens(String authToken) {
        return null;
    }

    public Token addToken(String authToken, Token token) {
        return null;
    }

    public Token updateToken(String authToken, String id, Token token) {
        return null;
    }

    public Token removeToken(String authToken, String id) {
        return null;
    }
}
