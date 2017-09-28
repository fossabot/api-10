package com.dongfg.project.api.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class MongoServiceTest {

    @Autowired
    private MongoService mongoService;

    @Test
    public void getCollections() throws Exception {
        List<String> collectionNames = mongoService.getCollections();
        Assert.assertNotNull(collectionNames);
        Assert.assertEquals(1, collectionNames.size());
        System.out.println(collectionNames);
    }

    @Test
    public void getCollectionData() throws Exception {
        List<String> collectionNames = mongoService.getCollections();
        collectionNames.forEach(name -> {
            List<String> collectionData = mongoService.getCollectionData(name);
            Assert.assertNotNull(collectionData);
            Assert.assertEquals(2, collectionData.size());
            System.out.println(collectionData);
        });
    }

}