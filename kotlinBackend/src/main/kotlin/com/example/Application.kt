package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.models.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import io.ktor.server.plugins.cors.*

fun main() {
    embeddedServer(Netty, port = 9000, host = "0.0.0.0") {
        install(CORS){
            allowMethod(HttpMethod.Options)
            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Post)
            allowHeaders { true }
            allowNonSimpleContentTypes = true
            allowCredentials = true
            anyHost()
            exposeHeader("key")
        }
        routing {
            get("/api"){
                call.respondText(Json.encodeToString(itemList), ContentType.Text.Plain)
            }
            post("/api/cart"){
                val post = call.receive<String>()
                call.respondText("Received $post from the cart request.", ContentType.Text.Plain)
            }
            post("/api/payment"){
                val post = call.receive<String>()
                call.application.environment.log.info("Incoming payment request: $post")
                call.respondText("Received $post from the payment request.", ContentType.Text.Plain)
            }
        }
//        configureRouting()
    }.start(wait = true)
}

