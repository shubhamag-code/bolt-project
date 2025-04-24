package com.bolt.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray

object DnDApiService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }


    private const val baseUrl = "https://www.dnd5eapi.co"

    suspend fun getSummary(): Map<String, Int> {
        val endpoints = listOf("classes", "spells", "monsters", "features")
        val results = endpoints.associate {
            (it to client.get("$baseUrl/api/$it").body<JsonObject>()["results"]?.jsonArray?.size)
        }

        return mapOf(
            "total_classes" to results["classes"]!!,
            "total_spells" to results["spells"]!!,
            "total_monsters" to results["monsters"]!!,
            "total_features" to results["features"]!!
        )
    }

    suspend fun getClassDetails(className: String): Map<String, JsonElement>? {
        val response = client.get("$baseUrl/api/classes/$className")
        if (response.status.value != 200) return null

        val json = response.body<JsonObject>()
        return mapOf(
            "name" to json["name"]!!,
            "hit_die" to json["hit_die"]!!,
            "proficiency_choices" to json["proficiency_choices"]!!,
            "saving_throws" to json["saving_throws"]!!
        )
    }
}