package com.bolt.routes


import com.bolt.service.DnDApiService
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.http.*

fun Route.classRoute() {
    get("/classes/{className}") {
        val className = call.parameters["className"] ?: return@get call.respondText(
            "Missing class name", status = HttpStatusCode.BadRequest
        )

        val result = DnDApiService.getClassDetails(className)
        if (result == null) {
            call.respond(HttpStatusCode.NotFound, "Class not found")
        } else {
            call.respond(result)
        }
    }
}
