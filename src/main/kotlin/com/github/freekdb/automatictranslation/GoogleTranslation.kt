package com.github.freekdb.automatictranslation

class GoogleTranslation {
    fun translate(sourceText: String, targetLanguage: String): TranslateResponse {
        return TranslateResponse(
            listOf(
                SourceTranslateResponse(
                    sourceText, "nl",
                    mapOf(targetLanguage to "Testing..."), ""
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
