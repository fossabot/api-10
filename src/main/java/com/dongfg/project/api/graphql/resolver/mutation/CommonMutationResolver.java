package com.dongfg.project.api.graphql.resolver.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.dongfg.project.api.graphql.type.Comment;
import com.dongfg.project.api.service.CommonService;
import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.GraphQLContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dongfg
 * @date 2017/10/15
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonMutationResolver implements GraphQLMutationResolver {

    @NonNull
    private CommonService commonService;

    public Comment addComment(Comment input, DataFetchingEnvironment env) {
        GraphQLContext context = env.getContext();
        if (context.getRequest().isPresent()) {
            input.setClientIp(context.getRequest().get().getRemoteAddr());
        }

        return commonService.addComment(input);
    }
}
