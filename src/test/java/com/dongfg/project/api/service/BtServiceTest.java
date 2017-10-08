package com.dongfg.project.api.service;

import com.dongfg.project.api.graphql.type.BtInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class BtServiceTest {

    @Autowired
    private BtService btService;

    @Test
    public void btSearch() throws Exception {
        List<String> keyWords = new ArrayList<>();
        keyWords.add("k1");

        List<BtInfo> btInfos = btService.btSearch(keyWords);
        System.out.println("=====" + btInfos.size() + "=====");
        btInfos.forEach(System.out::println);
    }

    @Test
    public void limitTest() {
        List<String> keyWords = new ArrayList<>();
        keyWords.add("k1");
        keyWords.add("k2");
        keyWords.add("k3");
        keyWords.add("k4");
        keyWords.add("k5");
        keyWords.add("k6");
        keyWords.add("k7");

        keyWords.stream().limit(5).forEach(System.out::println);
    }

}