package com.dongfg.project.api.graphql.resolver.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.graphql.type.BtInfo;
import com.dongfg.project.api.service.BtService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dongfg
 * @date 2017/10/15
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BtQueryResolver implements GraphQLQueryResolver {

    @NonNull
    private BtService btService;

    public List<BtInfo> btSearch(List<String> keyWordsList) {
        return keyWordsList.parallelStream()
                .map(keyWords -> btService.btSearch(keyWords))
                .collect(Collectors.toList());
    }
}
