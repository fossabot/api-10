package com.dongfg.project.api.service;

import com.dongfg.project.api.graphql.type.TotpToken;
import com.dongfg.project.api.repository.TokenRepository;
import com.dongfg.project.api.util.SecurityContextHolder;
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
public class CoolhubService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private WechatService wechatService;


    /**
     * 查询当前用户所用的电子令牌
     *
     * @return 当前用户所用的电子令牌
     */
    public List<TotpToken> tokens() {
        return tokenRepository.findAll(Example.of(TotpToken.builder()
                .openId(SecurityContextHolder.getCurrent()).build()));
    }

    /**
     * 新增电子令牌
     *
     * @param totpToken 电子令牌
     * @return 电子令牌
     */
    public TotpToken createToken(TotpToken totpToken) {
        totpToken.setOpenId(SecurityContextHolder.getCurrent());
        return tokenRepository.save(totpToken);
    }

    /**
     * 更新电子令牌
     *
     * @param totpToken 电子令牌
     * @return 更新后的数据
     */
    public TotpToken updateToken(TotpToken totpToken) {
        TotpToken record = tokenRepository.findOne(totpToken.getId());
        if (record == null) {
            record = totpToken;
        } else {
            record.setAppName(totpToken.getAppName());
            record.setUserName(totpToken.getUserName());
            record.setSecretKey(totpToken.getSecretKey());
        }
        return tokenRepository.save(record);
    }

    /**
     * 删除电子令牌
     *
     * @param id 令牌ID
     * @return 原始数据
     */
    public TotpToken removeToken(String id) {
        TotpToken record = tokenRepository.findOne(id);
        tokenRepository.delete(id);
        return record;
    }
}
