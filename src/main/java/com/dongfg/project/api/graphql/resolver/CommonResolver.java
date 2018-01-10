package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.graphql.type.Comment;
import com.dongfg.project.api.service.CommonService;
import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.GraphQLContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dongfg
 * @date 17-11-22
 */
@Component
@Slf4j
public class CommonResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    @Autowired
    private CommonService commonService;

    public Comment createComment(Comment input, DataFetchingEnvironment env) {
        GraphQLContext context = env.getContext();
        if (context.getRequest().isPresent()) {
            input.setClientIp(context.getRequest().get().getRemoteAddr());
        }

        return commonService.createComment(input);
    }

    public List<Comment> comments() {
        return commonService.findAll();
    }
}
