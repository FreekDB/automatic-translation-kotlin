package com.github.freekdb.automatictranslation.translate.google

import com.google.cloud.translate.Translate

class GoogleTranslation(private val googleTranslate: Translate) {
    fun translate(sourceTexts: List<String>, targetLanguages: List<String>): TranslateResponse {
        val detectedSourceLanguages = mutableMapOf<String, String>()
        val translations = mutableMapOf<String, MutableMap<String, String>>()

        targetLanguages.map { targetLanguage ->
            val translateOption = Translate.TranslateOption.targetLanguage(targetLanguage)
            val translationsByLanguage = googleTranslate.translate(sourceTexts, translateOption)

            translationsByLanguage.forEachIndexed { translationIndex, translation ->
                val sourceText = sourceTexts[translationIndex]
                detectedSourceLanguages[sourceText] = translation.sourceLanguage
                translations.getOrPut(sourceText) { mutableMapOf() }[targetLanguage] = translation.translatedText
            }
        }

        return TranslateResponse(
            sourceTexts.map { sourceText ->
                SourceTranslateResponse(
                    sourceText,
                    detectedSourceLanguages[sourceText] ?: "",
                    translations[sourceText] ?: emptyMap(),
                    ""
                )
            }
        )
    }
}


data class TranslateRequest(val sourceTexts: List<String>, val targetLanguages: List<String>)

data class TranslateResponse(val data: List<SourceTranslateResponse>)

data class SourceTranslateResponse(
    val sourceText: String, val detectedSourceLanguage: String,
    val translations: Map<String, String>, val errors: String
)
