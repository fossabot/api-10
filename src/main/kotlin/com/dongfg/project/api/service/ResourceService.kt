package com.dongfg.project.api.service

import com.dongfg.project.api.component.ZiMuZu
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ResourceService {
    @Autowired
    private lateinit var ziMuZu: ZiMuZu
}