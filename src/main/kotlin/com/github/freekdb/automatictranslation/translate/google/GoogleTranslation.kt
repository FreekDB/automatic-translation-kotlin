package com.github.freekdb.automatictranslation.translate.google

import com.github.freekdb.automatictranslation.translate.TranslationApi
import com.google.cloud.translate.Translate

private val FULLY_SUPPORTED_LANGUAGES = listOf(
    "da",  // Danish
    "nl",  // Dutch
    "en",  // English
    "fi",  // Finnish
    "fr",  // French
    "de",  // German
    "hu",  // Hungarian
    "it",  // Italian
    "no",  // Norwegian
    "pl",  // Polish
    "pt",  // Portuguese
    "es",  // Spanish
    "sv"   // Swedish
)

private val ACCEPTED_CHINESE_DIALECTS = listOf(
    "zh-CN",  // Chinese (simplified)
    "zh-TW"   // Chinese (traditional)
)

private val ACCEPTED_LANGUAGES = listOf(
    "hr",  // Croatian
    "cs",  // Czech
    "et",  // Estonian
    "el",  // Greek
    "he",  // Hebrew
    "iw",  // Hebrew
    "is",  // Icelandic
    "ja",  // Japanese
    "ko",  // Korean
    "lv",  // Latvian
    "lt",  // Lithuanian
    "ro",  // Romanian
    "ru",  // Russian
    "sk",  // Slovak
    "sl",  // Slovenian
    "tr"   // Turkish
)

/**
 * See https://cloud.google.com/translate/docs/languages for all languages supported by Google Translation.
 */
private val SUPPORTED_LANGUAGES = FULLY_SUPPORTED_LANGUAGES + ACCEPTED_CHINESE_DIALECTS + ACCEPTED_LANGUAGES

class GoogleTranslation(private val googleTranslate: Translate) : TranslationApi {
    override fun getSupportedLanguages(): List<String> {
        return SUPPORTED_LANGUAGES
    }

    override fun translateTexts(sourceTexts: List<String>, targetLanguages: List<String>): TranslateResponse {
        val detectedSourceLanguages = mutableMapOf<String, String>()
        val translations = mutableMapOf<String, MutableMap<String, String>>()
        val errors = mutableListOf<String>()

        targetLanguages.map { targetLanguage ->
            val translateOption = Translate.TranslateOption.targetLanguage(targetLanguage)

            try {
                val translationsByLanguage = googleTranslate.translate(sourceTexts, translateOption)

                translationsByLanguage.forEachIndexed { translationIndex, translation ->
                    val sourceText = sourceTexts[translationIndex]
                    detectedSourceLanguages[sourceText] = translation.sourceLanguage
                    translations.getOrPut(sourceText) { mutableMapOf() }[targetLanguage] = translation.translatedText
                }
            } catch (e: Exception) {
                errors += e.message ?: "Exception during translation."
            }
        }

        return TranslateResponse(
            sourceTexts.map { sourceText ->
                SourceTranslateResponse(
                    sourceText,
                    detectedSourceLanguages[sourceText] ?: "",
                    translations[sourceText] ?: emptyMap(),
                    errors.joinToString()
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
