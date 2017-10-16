package com.dongfg.project.api.graphql.resolver.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.graphql.type.Comment;
import com.dongfg.project.api.repository.CommentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dongfg
 * @date 2017/10/15
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonQueryResolver implements GraphQLQueryResolver {

    @NonNull
    private CommentRepository commentRepository;

    public List<Comment> getCommentList() {
        return commentRepository.findAll();
    }

}
