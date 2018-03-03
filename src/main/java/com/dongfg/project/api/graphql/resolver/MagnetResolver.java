package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.graphql.type.Magnet;
import com.dongfg.project.api.service.MagnetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dongfg
 * @date 2017/10/15
 */
@Component
@Slf4j
public class MagnetResolver implements GraphQLQueryResolver {

    @Autowired
    private MagnetService magnetService;

    public List<Magnet> magnets(List<String> keyWordsList) {
        return keyWordsList.parallelStream()
                .map(keyWords -> magnetService.searchByKeyWords(keyWords))
                .collect(Collectors.toList());
    }
}
