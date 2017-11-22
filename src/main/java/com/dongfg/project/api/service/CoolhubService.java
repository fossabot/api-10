package com.dongfg.project.api.service;

import com.dongfg.project.api.graphql.type.Token;
import com.dongfg.project.api.repository.TokenRepository;
import com.dongfg.project.api.util.SecurityContextHolder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dongfg
 * @date 17-11-22
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CoolhubService {

    @NonNull
    private TokenRepository tokenRepository;

    @NonNull
    private WechatService wechatService;


    /**
     * 查询当前用户所用的电子令牌
     *
     * @return 当前用户所用的电子令牌
     */
    public List<Token> loadTokens() {
        return tokenRepository.findAll(Example.of(Token.builder()
                .openId(SecurityContextHolder.getCurrent()).build()));
    }

    /**
     * 新增电子令牌
     *
     * @param token 电子令牌
     * @return 电子令牌
     */
    public Token addToken(Token token) {
        token.setOpenId(SecurityContextHolder.getCurrent());
        return tokenRepository.save(token);
    }

    /**
     * 更新电子令牌
     *
     * @param id    令牌ID
     * @param token 电子令牌
     * @return 更新后的数据
     */
    public Token updateToken(String id, Token token) {
        Token record = tokenRepository.findOne(id);
        if (record == null) {
            record = token;
        } else {
            record.setAppName(token.getAppName());
            record.setUserName(token.getUserName());
            record.setSecretKey(token.getSecretKey());
        }
        return tokenRepository.save(record);
    }

    /**
     * 删除电子令牌
     *
     * @param id 令牌ID
     * @return 原始数据
     */
    public Token removeToken(String id) {
        Token record = tokenRepository.findOne(id);
        tokenRepository.delete(id);
        return record;
    }
}
