package com.github.freekdb.automatictranslation.plugins

import com.github.freekdb.automatictranslation.translate.google.GoogleTranslator
import com.github.freekdb.automatictranslation.translate.google.TranslateRequest
import com.github.freekdb.automatictranslation.translate.google.GoogleTranslateSupplier
import com.github.freekdb.automatictranslation.translate.languages.LanguageCleaner
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
            val googleTranslator = GoogleTranslator(GoogleTranslateSupplier().getGoogleTranslate())

            call.respond(googleTranslator.translateTexts(translateRequest.sourceTexts,
                LanguageCleaner().clean(translateRequest.targetLanguages, googleTranslator)))
        }
    }
}
