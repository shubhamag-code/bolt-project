package com.bolt.config

import com.bolt.api.dndRoutes
import com.bolt.di.AppModule
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT")?.toIntOrNull() ?: 8080) {
        configureKtor()
    }.start(wait = true)
}

fun Application.configureKtor() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        dndRoutes(AppModule.dndService)
    }
}