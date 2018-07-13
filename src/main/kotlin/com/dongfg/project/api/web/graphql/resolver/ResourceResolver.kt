package com.dongfg.project.api.web.graphql.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.dongfg.project.api.component.ZiMuZu
import com.dongfg.project.api.model.ResourceDetail
import com.dongfg.project.api.model.ResourceInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ResourceResolver : GraphQLQueryResolver {

    @Autowired
    private lateinit var ziMuZu: ZiMuZu

    fun resources(keyword: String): List<ResourceInfo> {
        return ziMuZu.search(keyword)
    }

    fun resourceDetail(resourceId: String): ResourceDetail {
        return ziMuZu.detail(resourceId)
    }
}