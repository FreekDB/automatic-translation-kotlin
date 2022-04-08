package com.github.freekdb.automatictranslation.plugins

import com.github.freekdb.automatictranslation.translate.google.GoogleTranslation
import com.github.freekdb.automatictranslation.translate.google.TranslateRequest
import com.github.freekdb.automatictranslation.translate.google.GoogleTranslateSupplier
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing

fun Application.configureRouting() {
    routing {
        get("/translate") {
            val translateRequest = call.receive<TranslateRequest>()
            val googleTranslation = GoogleTranslation(GoogleTranslateSupplier().getGoogleTranslate())
            call.respond(googleTranslation.translate(translateRequest.sourceTexts, translateRequest.targetLanguages))
        }
    }
}
