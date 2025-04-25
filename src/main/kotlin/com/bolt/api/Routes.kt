package com.bolt.api

import com.bolt.domain.service.DnDService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.dndRoutes(service: DnDService) {
    route("/summary") {
        get {
            call.respond(service.getSummary())
        }
    }

    route("/classes/{className}") {
        get {
            val className = call.parameters["className"]!!
            val detail = service.getClassDetails(className)
            if (detail == null) call.respondText("Not found", status = io.ktor.http.HttpStatusCode.NotFound)
            else call.respond(detail)
        }
    }
}
