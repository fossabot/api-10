package com.dongfg.project.api.common

import com.dongfg.project.api.common.util.KeywordSerializer
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

interface WeChatTemplate {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    data class ScheduleReminder(
            var touser: String,
            @JsonProperty("template_id")
            var id: String = "4tbzwXEkWSuYugVMrxkQDy1807mMXXaFyyTBGKu-Vkg",
            var page: String? = null,
            @JsonProperty("form_id")
            var formId: String,
            var data: ScheduleReminderData,
            @JsonProperty("emphasis_keyword")
            var emphasisKeyword: String? = null

    ) {
        data class ScheduleReminderData(
                @JsonSerialize(using = KeywordSerializer::class)
                var topic: String,
                @JsonSerialize(using = KeywordSerializer::class)
                var time: String,
                @JsonSerialize(using = KeywordSerializer::class)
                var location: String,
                @JsonSerialize(using = KeywordSerializer::class)
                var desc: String,
                @JsonSerialize(using = KeywordSerializer::class)
                var level: String
        )
    }
}