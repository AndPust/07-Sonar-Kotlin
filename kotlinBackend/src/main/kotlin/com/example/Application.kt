package com.example
import com.example.models.itemList
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.cors.CORS
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, port = 9000, host = "0.0.0.0") {
        install(CORS) {
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
            get("/api") {
                call.respondText(Json.encodeToString(itemList), ContentType.Text.Plain)
            }
            post("/api/cart") {
                val post = call.receive<String>()
                call.respondText("Received $post from the cart request.", ContentType.Text.Plain)
            }
            post("/api/payment") {
                val post = call.receive<String>()
                call.application.environment.log.info("Incoming payment request: $post")
                call.respondText("Received $post from the payment request.", ContentType.Text.Plain)
            }
        }
//        configureRouting()
    }.start(wait = true)
}
