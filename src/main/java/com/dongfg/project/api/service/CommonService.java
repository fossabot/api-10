package com.dongfg.project.api.service;

import com.dongfg.project.api.graphql.type.Comment;
import com.dongfg.project.api.repository.CommentRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author dongfg
 * @date 2017/10/15
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonService {

    private static GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

    @NonNull
    private CommentRepository commentRepository;

    @Value("${otp.secret:change}")
    private String otpSecret;

    public Comment addComment(Comment input) {
        input.setCreateTime(new Date());
        return commentRepository.save(input);
    }

    public boolean validateOtpCode(int otpCode) {
        return googleAuthenticator.authorize(otpSecret, otpCode);
    }
}
