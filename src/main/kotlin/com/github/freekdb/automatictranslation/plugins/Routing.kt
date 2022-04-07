package com.github.freekdb.automatictranslation.plugins

import com.github.freekdb.automatictranslation.GoogleTranslation
import com.github.freekdb.automatictranslation.translate.google.GoogleTranslateSupplier
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing

fun Application.configureRouting() {
    routing {
        get("/translate") {
            val googleTranslate = GoogleTranslateSupplier().getGoogleTranslate()
            val googleTranslation = GoogleTranslation(googleTranslate)
            call.respond(googleTranslation.translate("Dit is een test...", "en"))
        }
    }
}
