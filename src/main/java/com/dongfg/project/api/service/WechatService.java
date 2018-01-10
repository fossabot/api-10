package com.dongfg.project.api.service;

import com.dongfg.project.api.component.WechatComponent;
import com.dongfg.project.api.dto.Code2SessionResponse;
import com.dongfg.project.api.entity.WechatSession;
import com.dongfg.project.api.graphql.payload.SessionPayload;
import com.dongfg.project.api.repository.WechatSessionRepository;
import com.dongfg.project.api.util.Constants;
import io.undertow.server.session.SecureRandomSessionIdGenerator;
import io.undertow.server.session.SessionIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author dongfg
 * @date 17-11-22
 */
@Service
@Slf4j
public class WechatService {

    private static final SessionIdGenerator SESSION_ID_GENERATOR = new SecureRandomSessionIdGenerator();

    @Autowired
    private WechatComponent wechatComponent;

    @Autowired
    private WechatSessionRepository wechatSessionRepository;

    /**
     * 微信登录，登录成功生成session 返回
     *
     * @param code wechat code
     * @return 3rd session
     */
    public SessionPayload wechatLogin(String code) {
        SessionPayload session = new SessionPayload();
        session.setCode(Constants.CodeConstants.SUCCESS);
        Optional<Code2SessionResponse> sessionOptional = wechatComponent.code2Session(code);
        if (!sessionOptional.isPresent()) {
            session.setCode(Constants.CodeConstants.NETWORK_ERROR);
            return session;
        }

        Code2SessionResponse wechatSession = sessionOptional.get();
        if (StringUtils.isEmpty(wechatSession.getOpenId())) {
            log.error("wechatComponent#code2Session,code:{},msg:{}", wechatSession.getErrCode(), wechatSession.getErrMsg());
            session.setCode(wechatSession.getErrCode());
        }

        // 生成sessionId返回
        String sessionId = SESSION_ID_GENERATOR.createSessionId();
        session.setSessionId(sessionId);

        // 保存
        WechatSession record = wechatSessionRepository.findOne(Example.of(WechatSession.builder()
                .openId(wechatSession.getOpenId()).build()));
        if (record == null) {
            record = WechatSession.builder().openId(wechatSession.getOpenId()).build();
        }

        record.setSessionId(sessionId);
        record.setSessionKey(wechatSession.getSessionKey());
        wechatSessionRepository.save(record);

        return session;
    }

    /**
     * 检查sessionId是否有效
     *
     * @param sessionId 登录返回的sessionId
     * @return sessionId状态
     */
    public WechatSession loadSession(String sessionId) {
        return wechatSessionRepository.findOne(Example.of(WechatSession.builder()
                .sessionId(sessionId).build()));
    }
}
