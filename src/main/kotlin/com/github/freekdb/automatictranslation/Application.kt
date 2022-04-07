package com.github.freekdb.automatictranslation

import com.github.freekdb.automatictranslation.plugins.configureRouting
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8086, host = "0.0.0.0") {
        install(ContentNegotiation) {
            gson()
        }
        configureRouting()
    }.start(wait = true)
}
