package com.dongfg.project.api.common.util

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

class KeywordSerializer : JsonSerializer<String>() {
    override fun serialize(value: String?, gen: JsonGenerator, serializers: SerializerProvider?) {
        val keywordMap = HashMap<String, String?>()
        keywordMap["value"] = value
        gen.writeObject(keywordMap)
    }
}