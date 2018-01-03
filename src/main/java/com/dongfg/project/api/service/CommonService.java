package com.dongfg.project.api.service;

import com.dongfg.project.api.config.property.ApiProperty;
import com.dongfg.project.api.graphql.type.Comment;
import com.dongfg.project.api.repository.CommentRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author dongfg
 * @date 2017/10/15
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonService {

    private static final GoogleAuthenticator GOOGLE_AUTHENTICATOR = new GoogleAuthenticator();

    @NonNull
    private CommentRepository commentRepository;

    @NonNull
    private ApiProperty apiProperty;

    public Comment createComment(Comment input) {
        input.setCreateTime(new Date());
        return commentRepository.save(input);
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public boolean invalidOtpCode(int otpCode) {
        try {
            return !GOOGLE_AUTHENTICATOR.authorize(apiProperty.getOtpSecret(), otpCode);
        } catch (GoogleAuthenticatorException e) {
            return true;
        }
    }
}
