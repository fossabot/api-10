package com.dongfg.project.api.repository;

import com.dongfg.project.api.graphql.type.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
