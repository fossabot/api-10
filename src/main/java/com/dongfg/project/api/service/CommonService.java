package com.dongfg.project.api.service;

import com.dongfg.project.api.graphql.type.Comment;
import com.dongfg.project.api.graphql.type.Token;
import com.dongfg.project.api.repository.CommentRepository;
import com.dongfg.project.api.repository.TokenRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author dongfg
 * @date 2017/10/15
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonService {

    @NonNull
    private CommentRepository commentRepository;

    @NonNull
    private TokenRepository tokenRepository;

    public Comment addComment(Comment input) {
        input.setCreateTime(new Date());
        return commentRepository.save(input);
    }

    public boolean validateToken(String tokenStr) {
        Token token = tokenRepository.findOne(tokenStr);
        if (StringUtils.isEmpty(tokenStr) || token == null || !token.isEnabled()) {
            return false;
        }

        token.setRequestTimes(token.getRequestTimes() + 1);
        token.setLastRequestTime(new Date());
        tokenRepository.save(token);
        return true;
    }

}
