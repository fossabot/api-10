package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.graphql.type.Comment;
import com.dongfg.project.api.repository.CommentRepository;
import com.dongfg.project.api.service.CommonService;
import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.GraphQLContext;
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
public class CommonResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    @NonNull
    private CommonService commonService;

    @NonNull
    private CommentRepository commentRepository;

    public Comment addComment(Comment input, DataFetchingEnvironment env) {
        GraphQLContext context = env.getContext();
        if (context.getRequest().isPresent()) {
            input.setClientIp(context.getRequest().get().getRemoteAddr());
        }

        return commonService.addComment(input);
    }

    public List<Comment> getCommentList() {
        return commentRepository.findAll();
    }
}
