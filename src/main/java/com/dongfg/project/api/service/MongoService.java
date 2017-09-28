package com.dongfg.project.api.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoService {

    @NonNull
    private MongoTemplate mongoTemplate;

    public List<String> getCollections() {
        return new ArrayList<>(mongoTemplate.getCollectionNames());
    }

    @SuppressWarnings("unchecked")
    public List<String> getCollectionData(String collectionName) {

        ArrayList<String> collectionData = new ArrayList<>();

        List<HashMap> records = mongoTemplate.findAll(HashMap.class, collectionName);
        if (records.size() == 0) {
            return collectionData;
        }

        HashMap<Object, Object> title = records.get(0);
        collectionData.add(title.entrySet().stream()
                .map(e -> String.valueOf(e.getKey())).collect(Collectors.joining(",")));

        for (HashMap<Object, Object> r : records) {
            collectionData.add(r.entrySet().stream()
                    .map(e -> String.valueOf(e.getValue())).collect(Collectors.joining(",")));
        }

        return collectionData;
    }
}
