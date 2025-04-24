package com.bolt.routes


import com.bolt.service.DnDApiService
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.call

fun Route.summaryRoute() {
    get("/summary") {
        call.respond(DnDApiService.getSummary())
    }
}
