package com.dongfg.project.api.graphql.resolver.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.service.MongoService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoQueryResolver implements GraphQLQueryResolver {

    @NonNull
    private MongoService mongoService;

    public List<String> collectionNames() {
        return mongoService.getCollections();
    }

    public List<String> collectionData(String collectionName) {
        return mongoService.getCollectionData(collectionName);
    }

}
