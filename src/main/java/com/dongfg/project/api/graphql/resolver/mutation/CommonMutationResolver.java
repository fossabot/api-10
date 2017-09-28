package com.dongfg.project.api.graphql.resolver.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.dongfg.project.api.graphql.type.Comment;
import com.dongfg.project.api.repository.CommentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonMutationResolver implements GraphQLMutationResolver {

    @NonNull
    private CommentRepository commentRepository;

    public Comment addComment(Comment input) {
        input.setCreateTime(new Date());
        return commentRepository.save(input);
    }
}
