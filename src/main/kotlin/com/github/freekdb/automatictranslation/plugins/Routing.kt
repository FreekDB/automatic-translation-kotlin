package com.github.freekdb.automatictranslation.plugins

import com.github.freekdb.automatictranslation.GoogleTranslation
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing

fun Application.configureRouting() {
    routing {
        get("/translate") {
             call.respond(GoogleTranslation().translate("Dit is een test...", "en"))
        }
    }
}
