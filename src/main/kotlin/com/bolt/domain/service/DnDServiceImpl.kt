package com.bolt.domain.service

import com.bolt.domain.model.mapper.ClassDetailMapper
import com.bolt.domain.model.ClassDetail
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DnDServiceImpl(private val client: HttpClient) : DnDService {


    private val baseUrl = "https://www.dnd5eapi.co"
    private val logger: Logger = LoggerFactory.getLogger(DnDServiceImpl::class.java)

    override suspend fun getSummary(): Map<String, Int> {
        val endpoints = listOf("classes", "spells", "monsters", "features")
        val results = mutableMapOf<String, Int>()

        endpoints.forEach { endpoint ->
            try {
                logger.info("Fetching data for endpoint: $endpoint")
                val response = client.get("$baseUrl/api/$endpoint")
                val jsonResponse = response.body<JsonObject>()
                val count = jsonResponse["results"]?.jsonArray?.size ?: 0
                results[endpoint] = count
            } catch (e: Exception) {
                logger.error("Error fetching data from $endpoint: ${e.message}")
                results[endpoint] = 0  // Set to 0 if there's an error fetching the data
            }
        }

        return mapOf(
            "total_classes" to results.getOrDefault("classes", 0),
            "total_spells" to results.getOrDefault("spells", 0),
            "total_monsters" to results.getOrDefault("monsters", 0),
            "total_features" to results.getOrDefault("features", 0)
        )
    }

    override suspend fun getClassDetails(className: String): ClassDetail? {
        return try {
            logger.info("Fetching details for class: $className")
            val response = client.get("$baseUrl/api/classes/$className")

            if (response.status != HttpStatusCode.OK) {
                logger.error("Failed to fetch class details for $className. Status code: ${response.status}")
                return null
            }
            val json = response.body<JsonObject>()
            ClassDetailMapper.mapToClassDetail(json)
        } catch (e: ClientRequestException) {
            logger.error("Client error when fetching details for $className: ${e.message}")
            null
        } catch (e: ServerResponseException) {
            logger.error("Server error when fetching details for $className: ${e.message}")
            null
        } catch (e: Exception) {
            logger.error("Unexpected error when fetching details for $className: ${e.message}")
            null
        }
    }
}
