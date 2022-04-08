package com.github.freekdb.automatictranslation.plugins

import com.github.freekdb.automatictranslation.translate.endpoint.TranslationEndpoint
import com.github.freekdb.automatictranslation.translate.google.GoogleTranslateSupplier
import com.github.freekdb.automatictranslation.translate.google.GoogleTranslator
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing

fun Application.configureRouting() {
    routing {
        get("/translate") {
            val translator = GoogleTranslator(GoogleTranslateSupplier().getGoogleTranslate())
            call.respond(TranslationEndpoint().handleTranslateRequest(call.receive(), translator))
        }
    }
}
