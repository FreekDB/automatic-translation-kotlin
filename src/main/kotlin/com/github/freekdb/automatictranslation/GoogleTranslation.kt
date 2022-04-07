package com.github.freekdb.automatictranslation

import com.google.cloud.translate.Translate

class GoogleTranslation(private val googleTranslate: Translate) {
    fun translate(sourceText: String, targetLanguage: String): TranslateResponse {
        val translation = googleTranslate.translate(sourceText, Translate.TranslateOption.targetLanguage(targetLanguage))

        return TranslateResponse(
            listOf(
                SourceTranslateResponse(
                    sourceText,
                    translation.sourceLanguage,
                    mapOf(targetLanguage to translation.translatedText),
                    ""
                )
            )
        )
    }
}


data class TranslateRequest(val sourceTexts: List<String>, val targetLanguages: List<String>)

data class TranslateResponse(val data: List<SourceTranslateResponse>)

data class SourceTranslateResponse(
    val sourceText: String, val detectedSourceLanguage: String,
    var translations: Map<String, String>, val errors: String
)
