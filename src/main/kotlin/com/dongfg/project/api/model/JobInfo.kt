package com.dongfg.project.api.model

import java.util.*

data class JobInfo(
    var name: String,
    var desc: String? = "-",
    var state: String? = null,
    var prevTime: Date? = null,
    var nextTime: Date? = null
)