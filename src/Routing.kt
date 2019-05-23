package com.example

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.ContentType
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post

fun Routing.root() {
    get("/") {
        call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
    }

    get("/demo") {
        call.respondText("Hello World Demo!", contentType = ContentType.Text.Plain)
    }

    get("/health_check") {
        // Check databases/other services.
        call.respondText("OK")
    }

    get("/html-freemarker") {
        call.respond(FreeMarkerContent("index.ftl", mapOf("data" to IndexData(listOf(1, 2, 3))), ""))
    }
    static("/static") {
        resources("static")
    }
}

fun Routing.login() {
    get ("/login") {
        call.respond(FreeMarkerContent("login.ftl", null))
    }
    post ("/login") {
        val post = call.receiveParameters()
        if (post["username"] != null && post["username"] == post["password"]) {
//            call.respondText("OK")
            call.respondRedirect("/html-freemarker", permanent = false)
        } else {
            call.respond(FreeMarkerContent("login.ftl", mapOf("error" to "Invalid login")))
        }
    }
}