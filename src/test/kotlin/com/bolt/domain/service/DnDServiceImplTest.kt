package com.bolt.domain.service

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.*
import kotlinx.serialization.json.Json

class DnDServiceImplTest : StringSpec({

    // Mocked response for summary
    val mockResponseSummary = """
        {
            "results": [
                { "index": "barbarian", "name": "Barbarian" },
                { "index": "bard", "name": "Bard" }
            ]
        }
    """.trimIndent()

    // Mocked response for class details (for "cleric")
    val mockResponseClassDetail = """
        {
            "name": "Cleric",
            "hit_die": 8,
            "proficiency_choices": [],
            "saving_throws": []
        }
    """.trimIndent()

    // JSON configuration for deserialization
    val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    // Mocking the HTTP client engine
    val mockEngine = MockEngine { request ->
        when {
            request.url.encodedPath.contains("api/classes/cleric") -> {
                respond(
                    mockResponseClassDetail, HttpStatusCode.OK, headersOf("Content-Type" to listOf("application/json"))
                )
            }

            request.url.encodedPath.endsWith("/api/classes") -> {
                respond(mockResponseSummary, HttpStatusCode.OK, headersOf("Content-Type" to listOf("application/json")))
            }

            request.url.encodedPath.contains("/api/spells") -> {
                respond(mockResponseSummary, HttpStatusCode.OK, headersOf("Content-Type" to listOf("application/json")))
            }

            request.url.encodedPath.contains("/api/monsters") -> {
                respond(mockResponseSummary, HttpStatusCode.OK, headersOf("Content-Type" to listOf("application/json")))
            }

            request.url.encodedPath.contains("/api/features") -> {
                respond(mockResponseSummary, HttpStatusCode.OK, headersOf("Content-Type" to listOf("application/json")))
            }

            else -> respond("{}", HttpStatusCode.NotFound)
        }
    }

    // HTTP client with mocked engine
    val client = HttpClient(mockEngine) {
        install(ContentNegotiation) {
            json(json)
        }
    }

    // DnDService instance
    val service = DnDServiceImpl(client)

    // Test for getSummary method
    "should fetch class summary and return correct count" {
        val result = service.getSummary()
        result["total_classes"] shouldBe 2
        result["total_spells"] shouldBe 2
        result["total_monsters"] shouldBe 2
        result["total_features"] shouldBe 2
    }

    "should fetch class details for Not found" {
        val classDetails = service.getClassDetails("xyz")
        classDetails.shouldBeNull()
    }

    "should fetch class details for Cleric" {
        val classDetails = service.getClassDetails("cleric")
        classDetails?.name shouldBe "Cleric"
        classDetails?.hitDie shouldBe 8
    }
})
