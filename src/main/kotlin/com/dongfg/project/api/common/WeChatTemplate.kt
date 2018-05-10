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
                @JsonProperty("keyword1")
                @JsonSerialize(using = KeywordSerializer::class)
                var topic: String,
                @JsonProperty("keyword2")
                @JsonSerialize(using = KeywordSerializer::class)
                var time: String,
                @JsonProperty("keyword3")
                @JsonSerialize(using = KeywordSerializer::class)
                var location: String,
                @JsonProperty("keyword4")
                @JsonSerialize(using = KeywordSerializer::class)
                var desc: String,
                @JsonProperty("keyword5")
                @JsonSerialize(using = KeywordSerializer::class)
                var level: String
        )
    }

    @JsonInclude(JsonInclude.Include.ALWAYS)
    data class TaskNotify(
            var touser: String,
            @JsonProperty("template_id")
            var id: String = "tile8v1AdvWNwBKbdDPSsyksVsKDZvdA2lbbnQk8pgQ",
            var page: String? = null,
            @JsonProperty("form_id")
            var formId: String,
            var data: TaskNotifyData,
            @JsonProperty("emphasis_keyword")
            var emphasisKeyword: String? = null
    ) {
        data class TaskNotifyData(
                @JsonProperty("keyword1")
                @JsonSerialize(using = KeywordSerializer::class)
                var time: String,
                @JsonProperty("keyword2")
                @JsonSerialize(using = KeywordSerializer::class)
                var name: String,
                @JsonProperty("keyword3")
                @JsonSerialize(using = KeywordSerializer::class)
                var type: String,
                @JsonProperty("keyword4")
                @JsonSerialize(using = KeywordSerializer::class)
                var status: String,
                @JsonProperty("keyword5")
                @JsonSerialize(using = KeywordSerializer::class)
                var desc: String
        )
    }
}