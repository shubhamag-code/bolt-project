package com.bolt.domain.service

import com.bolt.domain.model.mapper.ClassDetailMapper
import com.bolt.domain.model.ClassDetail
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray

class DnDServiceImpl(private val client: HttpClient) : DnDService {

    private val baseUrl = "https://www.dnd5eapi.co"

    override suspend fun getSummary(): Map<String, Int> {
        val endpoints = listOf("classes", "spells", "monsters", "features")
        val results = endpoints.associateWith {
            client.get("$baseUrl/api/$it").body<JsonObject>()["results"]?.jsonArray?.size ?: 0
        }

        return mapOf(
            "total_classes" to results.getOrDefault("classes", 0),
            "total_spells" to results.getOrDefault("spells", 0),
            "total_monsters" to results.getOrDefault("monsters", 0),
            "total_features" to results.getOrDefault("features", 0)
        )
    }

    override suspend fun getClassDetails(className: String): ClassDetail? {
        val response = client.get("$baseUrl/api/classes/$className")
        if (response.status.value != 200) {return null}

        val json = response.body<JsonObject>()
        return ClassDetailMapper.mapToClassDetail(json)
    }
}
